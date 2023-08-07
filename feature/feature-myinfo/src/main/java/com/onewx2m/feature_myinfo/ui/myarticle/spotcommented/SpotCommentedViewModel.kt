package com.onewx2m.feature_myinfo.ui.myarticle.spotcommented

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
import com.onewx2m.domain.usecase.GetSpotCommentedUseCase
import com.onewx2m.mvi.MviViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SpotCommentedViewModel @Inject constructor(
    private val getSpotCommentedUseCase: GetSpotCommentedUseCase,
    private val bookmarkUseCase: BookmarkSpotUseCase,
) :
    MviViewModel<SpotCommentedViewState, SpotCommentedEvent, SpotCommentedSideEffect>(
        SpotCommentedViewState(),
    ) {
    private var cursorId: Int = 0
    private var last: Boolean = false

    override fun reduceState(
        current: SpotCommentedViewState,
        event: SpotCommentedEvent,
    ): SpotCommentedViewState {
        return when (event) {
            is SpotCommentedEvent.UpdateSpotList -> current.copy(spotList = event.spotList)
            SpotCommentedEvent.Initialized -> current.copy(
                isInitializing = false,
            )

            SpotCommentedEvent.Initializing -> current.copy(
                isInitializing = true,
            )
        }
    }

    fun getSpotCommented(needClear: Boolean = false) = viewModelScope.launch {
        if (needClear) {
            clearCursorAndList()
            postEvent(SpotCommentedEvent.Initializing)
        }

        if (last) return@launch

        getSpotCommentedUseCase(
            cursorId = cursorId,
        ).onCompletion {
            postEvent(SpotCommentedEvent.Initialized)
        }.collectOutcome(
            handleSuccess = ::handleGetLocationSuccess,
            handleFail = { handleError(it.error) },
        )
    }

    private fun handleGetLocationSuccess(outcome: Outcome.Success<SpotList>) {
        updateCursor(outcome)

        postEvent(
            SpotCommentedEvent.UpdateSpotList(
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
        postEvent(SpotCommentedEvent.UpdateSpotList(emptyList()))
    }

    private fun handleError(error: Throwable?) {
        when (error) {
            is CommonException.NeedLoginException -> {
                postSideEffect(SpotCommentedSideEffect.ShowErrorToast(error.snackBarMessage))
                postSideEffect(SpotCommentedSideEffect.GoToLoginFragment)
            }

            else -> postSideEffect(
                SpotCommentedSideEffect.ShowErrorToast(
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

        postEvent(SpotCommentedEvent.UpdateSpotList(bookmarkUpdatedItem))
    }

    fun goToSpotDetail(spotId: Int) {
        postSideEffect(SpotCommentedSideEffect.GoToSpotDetailFragment(spotId))
    }
}
