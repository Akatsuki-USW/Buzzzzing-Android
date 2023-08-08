package com.onewx2m.feature_bookmark.ui.bookmark

import android.os.Bundle
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.tabs.TabLayoutMediator
import com.onewx2m.core_ui.model.WRITE_CONTENT_KEY
import com.onewx2m.core_ui.model.WriteContent
import com.onewx2m.design_system.databinding.IncludeTabBookmarkBinding
import com.onewx2m.feature_bookmark.R
import com.onewx2m.feature_bookmark.databinding.FragmentBookmarkBinding
import com.onewx2m.feature_bookmark.ui.bookmark.adapter.BookmarkFragmentStateAdapter
import com.onewx2m.feature_bookmark.ui.bookmark.adapter.BookmarkViewPagerType
import com.onewx2m.feature_bookmark.ui.bookmark.buzzzzing.BookmarkBuzzzzingFragment
import com.onewx2m.feature_bookmark.ui.bookmark.buzzzzing.BookmarkBuzzzzingViewModel
import com.onewx2m.feature_bookmark.ui.bookmark.spot.BookmarkSpotFragment
import com.onewx2m.feature_bookmark.ui.bookmark.spot.BookmarkSpotViewModel
import com.onewx2m.mvi.MviFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BookmarkFragment :
    MviFragment<FragmentBookmarkBinding, BookmarkViewState, BookmarkEvent, BookmarkSideEffect, BookmarkViewModel>(
        FragmentBookmarkBinding::inflate,
    ) {
    override val viewModel: BookmarkViewModel by viewModels()
    private val bookmarkBuzzzzingViewModel: BookmarkBuzzzzingViewModel by viewModels()
    private val bookmarkSpotViewModel: BookmarkSpotViewModel by viewModels()

    private val pagerAdapter: BookmarkFragmentStateAdapter by lazy {
        BookmarkFragmentStateAdapter(
            this,
            listOf(BookmarkBuzzzzingFragment(), BookmarkSpotFragment()),
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bookmarkBuzzzzingViewModel.getBuzzzzingLocationBookmarked(true)
        bookmarkSpotViewModel.getSpotBookmarked(true)
        viewModel.viewPagerPosition = 0
    }

    override fun onResume() {
        super.onResume()

        if (binding.viewPager.adapter == null) {
            binding.viewPager.adapter = pagerAdapter
        }
        binding.viewPager.setCurrentItem(viewModel.viewPagerPosition, false)
    }

    override fun onPause() {
        super.onPause()

        viewModel.viewPagerPosition = binding.viewPager.currentItem
        binding.viewPager.adapter = null
    }

    override fun initView() {
        initViewPagerAndTabLayout()

        findNavController().currentBackStackEntry?.savedStateHandle?.getLiveData<WriteContent>(
            WRITE_CONTENT_KEY,
        )
            ?.observe(viewLifecycleOwner) { data ->
                bookmarkSpotViewModel.updateListItem(data)
            }
    }

    private fun initViewPagerAndTabLayout() {
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
