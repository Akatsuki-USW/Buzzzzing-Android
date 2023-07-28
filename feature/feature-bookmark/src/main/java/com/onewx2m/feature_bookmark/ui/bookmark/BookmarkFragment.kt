package com.onewx2m.feature_bookmark.ui.bookmark

import androidx.fragment.app.viewModels
import com.google.android.material.tabs.TabLayoutMediator
import com.onewx2m.design_system.databinding.IncludeTabBookmarkBinding
import com.onewx2m.feature_bookmark.R
import com.onewx2m.feature_bookmark.databinding.FragmentBookmarkBinding
import com.onewx2m.feature_bookmark.ui.bookmark.adapter.BookmarkFragmentStateAdapter
import com.onewx2m.feature_bookmark.ui.bookmark.adapter.BookmarkViewPagerType
import com.onewx2m.mvi.MviFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BookmarkFragment :
    MviFragment<FragmentBookmarkBinding, BookmarkViewState, BookmarkEvent, BookmarkSideEffect, BookmarkViewModel>(
        FragmentBookmarkBinding::inflate,
    ) {
    override val viewModel: BookmarkViewModel by viewModels()

    private var pagerAdapter: BookmarkFragmentStateAdapter? = null

    override fun initView() {
        initViewPagerAndTabLayout()
    }

    private fun initViewPagerAndTabLayout() {
        if (pagerAdapter != null) return

        pagerAdapter = BookmarkFragmentStateAdapter(this)

        binding.viewPager.apply {
            isUserInputEnabled = false
            adapter = pagerAdapter
        }

        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            tab.customView = getTabView(BookmarkViewPagerType.getType(position))
        }.attach()
    }

    private fun getTabView(type: BookmarkViewPagerType) =
        IncludeTabBookmarkBinding.inflate(layoutInflater).apply {
            textViewTab.text = getTabTitleString(type)
        }.root

    private fun getTabTitleString(type: BookmarkViewPagerType): String {
        return when (type) {
            BookmarkViewPagerType.BUZZZZING -> getString(R.string.fragment_tab_buzzzzing_place)
            BookmarkViewPagerType.SPOT -> getString(R.string.fragment_tab_spot_place)
        }
    }
}
