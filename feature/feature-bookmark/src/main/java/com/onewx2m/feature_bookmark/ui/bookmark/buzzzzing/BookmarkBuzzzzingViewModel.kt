package com.onewx2m.feature_bookmark.ui.bookmark.buzzzzing

import androidx.lifecycle.viewModelScope
import com.onewx2m.design_system.components.recyclerview.buzzzzingmedium.BuzzzzingMediumItem
import com.onewx2m.design_system.enum.ItemViewType
import com.onewx2m.domain.Outcome
import com.onewx2m.domain.collectOutcome
import com.onewx2m.domain.exception.common.CommonException
import com.onewx2m.domain.model.BuzzzzingContent
import com.onewx2m.domain.model.BuzzzzingLocation
import com.onewx2m.domain.model.BuzzzzingLocationBookmark
import com.onewx2m.domain.usecase.BookmarkBuzzzzingLocationUseCase
import com.onewx2m.domain.usecase.GetBuzzzzingLocationBookmarkedUseCase
import com.onewx2m.mvi.MviViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BookmarkBuzzzzingViewModel @Inject constructor(
    private val getBuzzzzingLocationBookmarkedUseCase: GetBuzzzzingLocationBookmarkedUseCase,
    private val bookmarkBuzzzzingLocationUseCase: BookmarkBuzzzzingLocationUseCase,
) :
    MviViewModel<BookmarkBuzzzzingViewState, BookmarkBuzzzzingEvent, BookmarkBuzzzzingSideEffect>(
        BookmarkBuzzzzingViewState(),
    ) {
    private var cursorId: Int = 0
    private var last: Boolean = false

    override fun reduceState(
        current: BookmarkBuzzzzingViewState,
        event: BookmarkBuzzzzingEvent,
    ): BookmarkBuzzzzingViewState {
        return when (event) {
            is BookmarkBuzzzzingEvent.UpdateBuzzzzingMediumItem -> current.copy(buzzzzingMediumItem = event.buzzzzingMediumItem)
            BookmarkBuzzzzingEvent.Initialized -> current.copy(
                isInitializing = false,
            )

            BookmarkBuzzzzingEvent.Initializing -> current.copy(
                isInitializing = true,
            )
        }
    }

    fun getBuzzzzingLocationBookmarked(needClear: Boolean = false) = viewModelScope.launch {
        if (needClear) {
            clearCursorAndList()
            postEvent(BookmarkBuzzzzingEvent.Initializing)
        }

        if (last) return@launch

        getBuzzzzingLocationBookmarkedUseCase(
            cursorId = cursorId,
        ).onCompletion {
            postEvent(BookmarkBuzzzzingEvent.Initialized)
        }.collectOutcome(
            handleSuccess = ::handleGetLocationSuccess,
            handleFail = { handleError(it.error) },
        )
    }

    private fun handleGetLocationSuccess(outcome: Outcome.Success<BuzzzzingLocation>) {
        updateCursor(outcome)

        postEvent(
            BookmarkBuzzzzingEvent.UpdateBuzzzzingMediumItem(
                getProcessedItem(outcome.data.content),
            ),
        )
    }

    private fun getProcessedItem(content: List<BuzzzzingContent>): List<BuzzzzingMediumItem> {
        val itemMapped = content.map { data ->
            BuzzzzingMediumItem(
                id = data.id,
                isBookmarked = data.isBookmarked,
                locationName = data.name,
                congestionSymbol = data.congestionSymbol,
                iconUrl = data.categoryIconUrl,
            )
        }

        val lastItem = if (last) {
            emptyList()
        } else {
            listOf(
                BuzzzzingMediumItem(type = ItemViewType.LOADING),
            )
        }

        return state.value.buzzzzingMediumItem
            .minus(BuzzzzingMediumItem(type = ItemViewType.LOADING))
            .plus(itemMapped)
            .plus(lastItem)
    }

    private fun updateCursor(outcome: Outcome.Success<BuzzzzingLocation>) {
        last = outcome.data.last
        outcome.data.content.lastOrNull()?.also { lastItem ->
            cursorId = lastItem.id
        }
    }

    private fun clearCursorAndList() {
        cursorId = 0
        last = false
        postEvent(BookmarkBuzzzzingEvent.UpdateBuzzzzingMediumItem(emptyList()))
    }

    private fun handleError(error: Throwable?) {
        when (error) {
            is CommonException.NeedLoginException -> {
                postSideEffect(BookmarkBuzzzzingSideEffect.ShowErrorToast(error.snackBarMessage))
                postSideEffect(BookmarkBuzzzzingSideEffect.GoToLoginFragment)
            }

            else -> postSideEffect(
                BookmarkBuzzzzingSideEffect.ShowErrorToast(
                    error?.message ?: CommonException.UnknownException().snackBarMessage,
                ),
            )
        }
    }

    fun bookmark(locationId: Int) = viewModelScope.launch {
        bookmarkBuzzzzingLocationUseCase(locationId).collectOutcome(
            handleSuccess = ::handleBookmarkSuccess,
            handleFail = { handleError(it.error) },
        )
    }

    private fun handleBookmarkSuccess(
        outcome: Outcome.Success<BuzzzzingLocationBookmark>,
    ) {
        val bookmarkUpdatedItem = state.value.buzzzzingMediumItem.filter {
            it.id != outcome.data.locationId
        }

        postEvent(BookmarkBuzzzzingEvent.UpdateBuzzzzingMediumItem(bookmarkUpdatedItem))
    }

    fun goToLocationDetailFragment(locationId: Int) {
        postSideEffect(BookmarkBuzzzzingSideEffect.GoToLocationDetailFragment(locationId))
    }
}
