package com.onewx2m.feature_bookmark.ui.bookmark.spot

import androidx.lifecycle.viewModelScope
import com.onewx2m.core_ui.model.WriteContent
import com.onewx2m.design_system.components.recyclerview.spot.SpotItem
import com.onewx2m.design_system.enum.ItemViewType
import com.onewx2m.domain.Outcome
import com.onewx2m.domain.collectOutcome
import com.onewx2m.domain.exception.common.CommonException
import com.onewx2m.domain.model.Spot
import com.onewx2m.domain.model.SpotBookmark
import com.onewx2m.domain.model.SpotList
import com.onewx2m.domain.usecase.BookmarkSpotUseCase
import com.onewx2m.domain.usecase.GetSpotBookmarkedUseCase
import com.onewx2m.mvi.MviViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BookmarkSpotViewModel @Inject constructor(
    private val getSpotBookmarkedUseCase: GetSpotBookmarkedUseCase,
    private val bookmarkSpotUseCase: BookmarkSpotUseCase,
) :
    MviViewModel<BookmarkSpotViewState, BookmarkSpotEvent, BookmarkSpotSideEffect>(
        BookmarkSpotViewState(),
    ) {
    private var cursorId: Int = 0
    private var last: Boolean = false

    override fun reduceState(
        current: BookmarkSpotViewState,
        event: BookmarkSpotEvent,
    ): BookmarkSpotViewState {
        return when (event) {
            is BookmarkSpotEvent.UpdateSpotList -> current.copy(spotList = event.spotList)
            BookmarkSpotEvent.Initialized -> current.copy(
                isInitializing = false,
            )

            BookmarkSpotEvent.Initializing -> current.copy(
                isInitializing = true,
            )
        }
    }

    fun getSpotBookmarked(needClear: Boolean = false) = viewModelScope.launch {
        if (needClear) {
            clearCursorAndList()
            postEvent(BookmarkSpotEvent.Initializing)
        }

        if (last) return@launch

        getSpotBookmarkedUseCase(
            cursorId = cursorId,
        ).onCompletion {
            postEvent(BookmarkSpotEvent.Initialized)
        }.collectOutcome(
            handleSuccess = ::handleGetLocationSuccess,
            handleFail = { handleError(it.error) },
        )
    }

    private fun handleGetLocationSuccess(outcome: Outcome.Success<SpotList>) {
        updateCursor(outcome)

        postEvent(
            BookmarkSpotEvent.UpdateSpotList(
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
        postEvent(BookmarkSpotEvent.UpdateSpotList(emptyList()))
    }

    private fun handleError(error: Throwable?) {
        when (error) {
            is CommonException.NeedLoginException -> {
                postSideEffect(BookmarkSpotSideEffect.ShowErrorToast(error.snackBarMessage))
                postSideEffect(BookmarkSpotSideEffect.GoToLoginFragment)
            }

            else -> postSideEffect(
                BookmarkSpotSideEffect.ShowErrorToast(
                    error?.message ?: CommonException.UnknownException().snackBarMessage,
                ),
            )
        }
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
        val bookmarkUpdatedItem = state.value.spotList.filter {
            it.id != outcome.data.spotId
        }

        postEvent(BookmarkSpotEvent.UpdateSpotList(bookmarkUpdatedItem))
    }

    fun goToSpotDetail(spotId: Int) {
        postSideEffect(BookmarkSpotSideEffect.GoToSpotDetailFragment(spotId))
    }

    fun updateListItem(writeContent: WriteContent) {
        val currentList = state.value.spotList.map {
            if (it.id == writeContent.spotId) {
                it.copy(
                    title = writeContent.title,
                    thumbnailImageUrl = writeContent.imgUrls.getOrNull(0),
                    address = writeContent.address,
                )
            } else {
                it
            }
        }

        postEvent(BookmarkSpotEvent.UpdateSpotList(currentList))
    }
}
