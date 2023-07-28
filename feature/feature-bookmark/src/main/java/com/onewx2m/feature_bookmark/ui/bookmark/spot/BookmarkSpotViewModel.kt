package com.onewx2m.feature_bookmark.ui.bookmark.spot

import com.onewx2m.mvi.MviViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class BookmarkSpotViewModel @Inject constructor() :
    MviViewModel<BookmarkSpotViewState, BookmarkSpotEvent, BookmarkSpotSideEffect>(
        BookmarkSpotViewState(),
    ) {
    override fun reduceState(
        current: BookmarkSpotViewState,
        event: BookmarkSpotEvent,
    ): BookmarkSpotViewState {
        return current
    }
}
