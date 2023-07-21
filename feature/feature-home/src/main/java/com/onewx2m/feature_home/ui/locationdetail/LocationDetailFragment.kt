package com.onewx2m.feature_home.ui.locationdetail

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.onewx2m.design_system.databinding.IncludeTabLocatoinDetailSelectedBinding
import com.onewx2m.design_system.databinding.IncludeTabLocatoinDetailUnselectedBinding
import com.onewx2m.feature_home.R
import com.onewx2m.feature_home.databinding.FragmentLocationDetailBinding
import com.onewx2m.feature_home.ui.locationdetail.adapter.LocationDetailFragmentStateAdapter
import com.onewx2m.feature_home.ui.locationdetail.adapter.LocationDetailViewPagerType
import com.onewx2m.mvi.MviFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LocationDetailFragment :
    MviFragment<FragmentLocationDetailBinding, LocationDetailViewState, LocationDetailEvent, LocationDetailSideEffect, LocationDetailViewModel>(
        FragmentLocationDetailBinding::inflate,
    ) {
    override val viewModel: LocationDetailViewModel by viewModels()
    private val navArgs: LocationDetailFragmentArgs by navArgs()

    private val pagerAdapter: LocationDetailFragmentStateAdapter by lazy {
        LocationDetailFragmentStateAdapter(this)
    }

    private val onTabSelectedListener = object : TabLayout.OnTabSelectedListener {
        override fun onTabSelected(tab: TabLayout.Tab) {
            tab.customView = getSelectedTabView(tab.text.toString())
        }

        override fun onTabUnselected(tab: TabLayout.Tab) {
            tab.customView = getUnSelectedTabView(tab.text.toString())
        }

        override fun onTabReselected(tab: TabLayout.Tab) {}
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.getLocationDetailUseCase(navArgs.locationId)
    }

    override fun initView() {
        initViewPagerAndTabLayout()
    }

    private fun initViewPagerAndTabLayout() {
        binding.viewPager2.adapter = pagerAdapter

        TabLayoutMediator(binding.tabLayout, binding.viewPager2) { tab, position ->
            tab.text = getTabTitleString(LocationDetailViewPagerType.getType(position))
            tab.customView = getTabView(LocationDetailViewPagerType.getType(position))
        }.attach()

        binding.tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                tab.customView = getSelectedTabView(tab.text.toString())
            }

            override fun onTabUnselected(tab: TabLayout.Tab) {
                tab.customView = getUnSelectedTabView(tab.text.toString())
            }

            override fun onTabReselected(tab: TabLayout.Tab) {}
        })
    }

    override fun onStart() {
        super.onStart()
        binding.tabLayout.addOnTabSelectedListener(onTabSelectedListener)
    }

    override fun onStop() {
        super.onStop()
        binding.tabLayout.removeOnTabSelectedListener(onTabSelectedListener)
    }

    private fun getSelectedTabView(text: String) =
        IncludeTabLocatoinDetailSelectedBinding.inflate(layoutInflater).apply {
            textViewTab.text = text
        }.root

    private fun getUnSelectedTabView(text: String) =
        IncludeTabLocatoinDetailUnselectedBinding.inflate(layoutInflater).apply {
            textViewTab.text = text
        }.root

    private fun getTabView(type: LocationDetailViewPagerType): View {
        return when (type) {
            LocationDetailViewPagerType.HISTORICAL_DATE -> getSelectedTabView(getTabTitleString(type))
            LocationDetailViewPagerType.RECOMMEND_PLACE_LIST -> getUnSelectedTabView(
                getTabTitleString(type),
            )
        }
    }

    private fun getTabTitleString(type: LocationDetailViewPagerType): String {
        return when (type) {
            LocationDetailViewPagerType.HISTORICAL_DATE -> getString(R.string.fragment_location_view_pager_tab_historical_data)
            LocationDetailViewPagerType.RECOMMEND_PLACE_LIST -> getString(R.string.fragment_location_view_pager_tab_recommend_place)
        }
    }
}
