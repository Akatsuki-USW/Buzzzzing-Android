package com.onewx2m.feature_myinfo.ui.myarticle

import android.os.Bundle
import androidx.fragment.app.viewModels
import com.google.android.material.tabs.TabLayoutMediator
import com.onewx2m.design_system.databinding.IncludeTabBookmarkBinding
import com.onewx2m.feature_myinfo.R
import com.onewx2m.feature_myinfo.databinding.FragmentMyArticleBinding
import com.onewx2m.feature_myinfo.ui.myarticle.adapter.MyArticleFragmentStateAdapter
import com.onewx2m.feature_myinfo.ui.myarticle.adapter.MyArticleViewPagerType
import com.onewx2m.feature_myinfo.ui.myarticle.spotcommented.SpotCommentedFragment
import com.onewx2m.feature_myinfo.ui.myarticle.spotcommented.SpotCommentedViewModel
import com.onewx2m.feature_myinfo.ui.myarticle.spotwritten.SpotWrittenFragment
import com.onewx2m.feature_myinfo.ui.myarticle.spotwritten.SpotWrittenViewModel
import com.onewx2m.mvi.MviFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MyArticleFragment :
    MviFragment<FragmentMyArticleBinding, MyArticleViewState, MyArticleEvent, MyArticleSideEffect, MyArticleViewModel>(
        FragmentMyArticleBinding::inflate,
    ) {
    override val viewModel: MyArticleViewModel by viewModels()
    private val spotWrittenViewModel: SpotWrittenViewModel by viewModels()
    private val spotCommentedViewModel: SpotCommentedViewModel by viewModels()

    private val pagerAdapter: MyArticleFragmentStateAdapter by lazy {
        MyArticleFragmentStateAdapter(
            this,
            listOf(SpotWrittenFragment(), SpotCommentedFragment()),
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.viewPagerPosition = 0
        spotWrittenViewModel.getSpotWritten()
        spotCommentedViewModel.getSpotCommented()
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
    }

    private fun initViewPagerAndTabLayout() {
        binding.viewPager.apply {
            isUserInputEnabled = false
            adapter = pagerAdapter
        }

        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            tab.customView = getTabView(MyArticleViewPagerType.getType(position))
        }.attach()
    }

    private fun getTabView(type: MyArticleViewPagerType) =
        IncludeTabBookmarkBinding.inflate(layoutInflater).apply {
            textViewTab.text = getTabTitleString(type)
        }.root

    private fun getTabTitleString(type: MyArticleViewPagerType): String {
        return when (type) {
            MyArticleViewPagerType.WRITTEN -> getString(R.string.fragment_tab_written)
            MyArticleViewPagerType.COMMENTED -> getString(R.string.fragment_tab_commented)
        }
    }
}
