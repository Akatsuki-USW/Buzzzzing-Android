package com.onewx2m.feature_bookmark.ui.bookmark.buzzzzing

import androidx.fragment.app.viewModels
import com.onewx2m.feature_bookmark.databinding.FragmentBookmarkBuzzzzingBinding
import com.onewx2m.mvi.MviFragment

class BookmarkBuzzzzingFragment :
    MviFragment<FragmentBookmarkBuzzzzingBinding, BookmarkBuzzzzingViewState, BookmarkBuzzzzingEvent, BookmarkBuzzzzingSideEffect, BookmarkBuzzzzingViewModel>(
        FragmentBookmarkBuzzzzingBinding::inflate,
    ) {
    override val viewModel: BookmarkBuzzzzingViewModel by viewModels()

    override fun initView() {
    }
}
