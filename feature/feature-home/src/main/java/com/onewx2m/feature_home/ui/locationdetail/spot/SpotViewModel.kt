package com.onewx2m.feature_home.ui.locationdetail.spot

import androidx.lifecycle.viewModelScope
import com.onewx2m.core_ui.util.ResourceProvider
import com.onewx2m.design_system.components.recyclerview.spot.SpotItem
import com.onewx2m.design_system.components.recyclerview.spotcategoryselector.SpotCategoryItem
import com.onewx2m.design_system.enum.ItemViewType
import com.onewx2m.domain.Outcome
import com.onewx2m.domain.collectOutcome
import com.onewx2m.domain.exception.common.CommonException
import com.onewx2m.domain.model.Spot
import com.onewx2m.domain.model.SpotBookmark
import com.onewx2m.domain.model.SpotList
import com.onewx2m.domain.usecase.BookmarkSpotUseCase
import com.onewx2m.domain.usecase.GetSpotCategoryUseCase
import com.onewx2m.domain.usecase.GetSpotOfLocationListUseCase
import com.onewx2m.feature_home.R
import com.onewx2m.mvi.MviViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SpotViewModel @Inject constructor(
    private val getSpotOfLocationListUseCase: GetSpotOfLocationListUseCase,
    private val bookmarkSpotUseCase: BookmarkSpotUseCase,
    private val getSpotCategoryUseCase: GetSpotCategoryUseCase,
    private val resourceProvider: ResourceProvider,
) : MviViewModel<SpotViewState, SpotEvent, SpotSideEffect>(
    SpotViewState(),
) {
    private var cursorId: Int = 0
    private var categoryId: Int? = null
    private var last: Boolean = false

    fun initData(locationId: Int) = viewModelScope.launch {
        initSpotCategoryItems()
        getSpotList(locationId)
    }

    private fun initSpotCategoryItems() = viewModelScope.launch(IO) {
        val categoryItems = listOf(
            SpotCategoryItem(
                id = null,
                name = resourceProvider.getString(com.onewx2m.core_ui.R.string.word_all),
            ),
        ) + getSpotCategoryUseCase().first()
            .map { SpotCategoryItem(id = it.id, name = it.name) }
        val selectedItem = categoryItems[0]

        postEvent(SpotEvent.UpdateSpotCategoryItems(categoryItems))
        postEvent(SpotEvent.UpdateSelectedSpotCategoryItem(selectedItem))
    }

    fun getSpotList(locationId: Int, needClear: Boolean = false) = viewModelScope.launch {
        if (needClear) {
            clearCursorAndList()
        }

        if (last) return@launch

        getSpotOfLocationListUseCase(
            cursorId = cursorId,
            categoryId = categoryId,
            locationId = locationId,
        ).collectOutcome(
            handleSuccess = ::handleGetSpotSuccess,
            handleFail = { handleError(it.error) },
        )
    }

    fun updateCategoryId(categoryId: Int?) {
        this.categoryId = categoryId
    }

    private fun handleGetSpotSuccess(outcome: Outcome.Success<SpotList>) {
        updateCursor(outcome)

        postEvent(
            SpotEvent.UpdateSpotList(
                getProcessedItem(outcome.data.content),
            ),
        )
    }

    private fun getProcessedItem(content: List<Spot>): List<SpotItem> {
        val itemMapped = content.map { data ->
            SpotItem(
                id = data.id,
                title = data.title,
                address = data.address,
                thumbnailImageUrl = data.thumbnailImageUrl,
                isBookmarked = data.isBookmarked,
                userNickname = data.userNickname,
                userProfileImageUrl = data.userProfileImageUrl,
            )
        }

        val lastItem = if (last) {
            emptyList()
        } else {
            listOf(
                SpotItem(type = ItemViewType.LOADING),
            )
        }

        return state.value.spotList
            .minus(SpotItem(type = ItemViewType.LOADING))
            .plus(itemMapped)
            .plus(lastItem)
    }

    private fun updateCursor(outcome: Outcome.Success<SpotList>) {
        last = outcome.data.last
        outcome.data.content.lastOrNull()?.also { lastItem ->
            cursorId = lastItem.id
        }
    }

    private fun clearCursorAndList() {
        cursorId = 0
        last = false
        postEvent(SpotEvent.UpdateSpotList(emptyList()))
    }

    fun bookmark(spotId: Int) = viewModelScope.launch {
        bookmarkSpotUseCase(spotId).collectOutcome(
            handleSuccess = ::handleBookmarkSuccess,
            handleFail = { handleError(it.error) },
        )
    }

    private fun handleBookmarkSuccess(
        outcome: Outcome.Success<SpotBookmark>,
    ) {
        val spotUpdatedItem = state.value.spotList.map {
            if (it.id == outcome.data.spotId) {
                it.copy(isBookmarked = outcome.data.isBookmarked)
            } else {
                it
            }
        }

        postEvent(SpotEvent.UpdateSpotList(spotUpdatedItem))
    }

    private fun handleError(error: Throwable?) {
        when (error) {
            is CommonException.NeedLoginException -> {
                postSideEffect(SpotSideEffect.ShowErrorToast(error.snackBarMessage))
                postSideEffect(SpotSideEffect.GoToLoginFragment)
            }

            else -> postSideEffect(
                SpotSideEffect.ShowErrorToast(
                    error?.message ?: CommonException.UnknownException().snackBarMessage,
                ),
            )
        }
    }

    fun goToSpotDetail(spotId: Int) {
        postSideEffect(SpotSideEffect.GoToSpotDetailFragment(spotId))
    }

    override fun reduceState(current: SpotViewState, event: SpotEvent): SpotViewState =
        when (event) {
            is SpotEvent.UpdateSelectedSpotCategoryItem -> current.copy(selectedSpotCategoryItem = event.selectedSpotCategoryItem)
            is SpotEvent.UpdateSpotCategoryItems -> current.copy(spotCategoryItems = event.spotCategoryItems)
            is SpotEvent.UpdateSpotList -> current.copy(spotList = event.spotList)
        }
}
