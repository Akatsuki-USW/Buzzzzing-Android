package com.onewx2m.feature_bookmark.ui.bookmark.spot

import androidx.fragment.app.viewModels
import com.onewx2m.feature_bookmark.databinding.FragmentBookmarkSpotBinding
import com.onewx2m.mvi.MviFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BookmarkSpotFragment :
    MviFragment<FragmentBookmarkSpotBinding, BookmarkSpotViewState, BookmarkSpotEvent, BookmarkSpotSideEffect, BookmarkSpotViewModel>(
        FragmentBookmarkSpotBinding::inflate,
    ) {
    override val viewModel: BookmarkSpotViewModel by viewModels()

    override fun initView() {
    }
}
