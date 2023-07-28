package com.onewx2m.feature_bookmark.ui.bookmark.buzzzzing

import com.onewx2m.mvi.MviViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class BookmarkBuzzzzingViewModel @Inject constructor() :
    MviViewModel<BookmarkBuzzzzingViewState, BookmarkBuzzzzingEvent, BookmarkBuzzzzingSideEffect>(
        BookmarkBuzzzzingViewState(),
    ) {
    override fun reduceState(
        current: BookmarkBuzzzzingViewState,
        event: BookmarkBuzzzzingEvent,
    ): BookmarkBuzzzzingViewState {
        return current
    }
}
