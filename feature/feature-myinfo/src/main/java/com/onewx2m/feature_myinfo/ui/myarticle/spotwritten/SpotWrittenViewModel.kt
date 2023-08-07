package com.onewx2m.feature_myinfo.ui.myarticle.spotwritten

import androidx.lifecycle.viewModelScope
import com.onewx2m.design_system.components.recyclerview.spot.SpotItem
import com.onewx2m.design_system.enum.ItemViewType
import com.onewx2m.domain.Outcome
import com.onewx2m.domain.collectOutcome
import com.onewx2m.domain.exception.common.CommonException
import com.onewx2m.domain.model.Spot
import com.onewx2m.domain.model.SpotBookmark
import com.onewx2m.domain.model.SpotList
import com.onewx2m.domain.usecase.BookmarkSpotUseCase
import com.onewx2m.domain.usecase.GetSpotWrittenUseCase
import com.onewx2m.mvi.MviViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SpotWrittenViewModel @Inject constructor(
    private val getSpotWrittenUseCase: GetSpotWrittenUseCase,
    private val bookmarkUseCase: BookmarkSpotUseCase,
) :
    MviViewModel<SpotWrittenViewState, SpotWrittenEvent, SpotWrittenSideEffect>(
        SpotWrittenViewState(),
    ) {
    private var cursorId: Int = 0
    private var last: Boolean = false

    override fun reduceState(
        current: SpotWrittenViewState,
        event: SpotWrittenEvent,
    ): SpotWrittenViewState {
        return when (event) {
            is SpotWrittenEvent.UpdateSpotList -> current.copy(spotList = event.spotList)
            SpotWrittenEvent.Initialized -> current.copy(
                isInitializing = false,
            )

            SpotWrittenEvent.Initializing -> current.copy(
                isInitializing = true,
            )
        }
    }

    fun getSpotWritten(needClear: Boolean = false) = viewModelScope.launch {
        if (needClear) {
            clearCursorAndList()
            postEvent(SpotWrittenEvent.Initializing)
        }

        if (last) return@launch

        getSpotWrittenUseCase(
            cursorId = cursorId,
        ).onCompletion {
            postEvent(SpotWrittenEvent.Initialized)
        }.collectOutcome(
            handleSuccess = ::handleGetLocationSuccess,
            handleFail = { handleError(it.error) },
        )
    }

    private fun handleGetLocationSuccess(outcome: Outcome.Success<SpotList>) {
        updateCursor(outcome)

        postEvent(
            SpotWrittenEvent.UpdateSpotList(
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
        postEvent(SpotWrittenEvent.UpdateSpotList(emptyList()))
    }

    private fun handleError(error: Throwable?) {
        when (error) {
            is CommonException.NeedLoginException -> {
                postSideEffect(SpotWrittenSideEffect.ShowErrorToast(error.snackBarMessage))
                postSideEffect(SpotWrittenSideEffect.GoToLoginFragment)
            }

            else -> postSideEffect(
                SpotWrittenSideEffect.ShowErrorToast(
                    error?.message ?: CommonException.UnknownException().snackBarMessage,
                ),
            )
        }
    }

    fun bookmark(spotId: Int) = viewModelScope.launch {
        bookmarkUseCase(spotId).collectOutcome(
            handleSuccess = ::handleBookmarkSuccess,
            handleFail = { handleError(it.error) },
        )
    }

    private fun handleBookmarkSuccess(
        outcome: Outcome.Success<SpotBookmark>,
    ) {
        val bookmarkUpdatedItem = state.value.spotList.filter {
            it.id != outcome.data.spotId
        }

        postEvent(SpotWrittenEvent.UpdateSpotList(bookmarkUpdatedItem))
    }

    fun goToSpotDetail(spotId: Int) {
        postSideEffect(SpotWrittenSideEffect.GoToSpotDetailFragment(spotId))
    }
}
