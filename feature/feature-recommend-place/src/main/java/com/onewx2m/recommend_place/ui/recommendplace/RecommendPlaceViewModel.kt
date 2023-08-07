package com.onewx2m.recommend_place.ui.recommendplace

import androidx.lifecycle.viewModelScope
import com.onewx2m.core_ui.R
import com.onewx2m.core_ui.model.WriteContent
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
import com.onewx2m.domain.usecase.GetAllSpotListUseCase
import com.onewx2m.domain.usecase.GetSpotCategoryUseCase
import com.onewx2m.mvi.MviViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RecommendPlaceViewModel @Inject constructor(
    private val getAllSpotListUseCase: GetAllSpotListUseCase,
    private val bookmarkSpotUseCase: BookmarkSpotUseCase,
    private val getSpotCategoryUseCase: GetSpotCategoryUseCase,
    private val resourceProvider: ResourceProvider,
) : MviViewModel<RecommendPlaceViewState, RecommendPlaceEvent, RecommendPlaceSideEffect>(
    RecommendPlaceViewState(),
) {
    private var cursorId: Int = 0
    private var categoryId: Int? = null
    private var last: Boolean = false

    fun initData() = viewModelScope.launch {
        initSpotCategoryItems()
        getSpotList()
    }

    private fun initSpotCategoryItems() = viewModelScope.launch(Dispatchers.IO) {
        val categoryItems = listOf(
            SpotCategoryItem(
                id = null,
                name = resourceProvider.getString(R.string.word_all),
            ),
        ) + getSpotCategoryUseCase().first()
            .map { SpotCategoryItem(id = it.id, name = it.name) }
        val selectedItem = categoryItems[0]

        postEvent(RecommendPlaceEvent.UpdateSpotCategoryItems(categoryItems))
        postEvent(RecommendPlaceEvent.UpdateSelectedSpotCategoryItem(selectedItem))
    }

    fun getSpotList(needClear: Boolean = false) = viewModelScope.launch {
        if (needClear) {
            clearCursorAndList()
        }

        if (last) return@launch

        getAllSpotListUseCase(
            cursorId = cursorId,
            categoryId = categoryId,
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
            RecommendPlaceEvent.UpdateSpotList(
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
        postEvent(RecommendPlaceEvent.UpdateSpotList(emptyList()))
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

        postEvent(RecommendPlaceEvent.UpdateSpotList(spotUpdatedItem))
    }

    private fun handleError(error: Throwable?) {
        when (error) {
            is CommonException.NeedLoginException -> {
                postSideEffect(RecommendPlaceSideEffect.ShowErrorToast(error.snackBarMessage))
                postSideEffect(RecommendPlaceSideEffect.GoToLoginFragment)
            }

            else -> postSideEffect(
                RecommendPlaceSideEffect.ShowErrorToast(
                    error?.message ?: CommonException.UnknownException().snackBarMessage,
                ),
            )
        }
    }

    fun postGoToWriteFragmentSideEffect() =
        postSideEffect(RecommendPlaceSideEffect.GoToWriteFragment)

    fun goToSpotDetail(spotId: Int) {
        postSideEffect(RecommendPlaceSideEffect.GoToSpotDetailFragment(spotId))
    }

    override fun reduceState(
        current: RecommendPlaceViewState,
        event: RecommendPlaceEvent,
    ): RecommendPlaceViewState =
        when (event) {
            is RecommendPlaceEvent.UpdateSelectedSpotCategoryItem -> current.copy(
                selectedSpotCategoryItem = event.selectedSpotCategoryItem,
            )

            is RecommendPlaceEvent.UpdateSpotCategoryItems -> current.copy(spotCategoryItems = event.spotCategoryItems)
            is RecommendPlaceEvent.UpdateSpotList -> current.copy(spotList = event.spotList)
        }
}
