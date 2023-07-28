package com.onewx2m.feature_bookmark.ui.bookmark

import androidx.fragment.app.viewModels
import com.onewx2m.feature_bookmark.databinding.FragmentBookmarkBinding
import com.onewx2m.mvi.MviFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BookmarkFragment :
    MviFragment<FragmentBookmarkBinding, BookmarkViewState, BookmarkEvent, BookmarkSideEffect, BookmarkViewModel>(
        FragmentBookmarkBinding::inflate,
    ) {
    override val viewModel: BookmarkViewModel by viewModels()

    override fun initView() {
    }
}
