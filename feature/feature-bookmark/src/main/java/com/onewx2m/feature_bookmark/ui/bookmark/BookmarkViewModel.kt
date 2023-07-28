package com.onewx2m.feature_bookmark.ui.bookmark

import com.onewx2m.mvi.MviViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class BookmarkViewModel @Inject constructor() : MviViewModel<BookmarkViewState, BookmarkEvent, BookmarkSideEffect>(
    BookmarkViewState()
) {
    override fun reduceState(current: BookmarkViewState, event: BookmarkEvent): BookmarkViewState {
        return current
    }
}