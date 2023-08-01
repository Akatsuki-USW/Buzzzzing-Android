package com.onewx2m.feature_home.ui.locationdetail

import android.os.Bundle
import android.view.View
import androidx.annotation.StringRes
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.onewx2m.core_ui.extensions.changeTextStyle
import com.onewx2m.core_ui.extensions.onThrottleClick
import com.onewx2m.core_ui.extensions.setGoneWithAnimation
import com.onewx2m.core_ui.extensions.setVisibleWithAnimation
import com.onewx2m.core_ui.util.DeepLinkUtil
import com.onewx2m.core_ui.util.TimeFormatter
import com.onewx2m.design_system.components.toast.ErrorToast
import com.onewx2m.design_system.databinding.IncludeTabLocatoinDetailSelectedBinding
import com.onewx2m.design_system.databinding.IncludeTabLocatoinDetailUnselectedBinding
import com.onewx2m.design_system.enum.Congestion
import com.onewx2m.design_system.enum.Congestion.*
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

    private var pagerAdapter: LocationDetailFragmentStateAdapter? = null

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
        binding.imageViewBack.onThrottleClick {
            viewModel.popBackStack()
        }
    }

    override fun render(current: LocationDetailViewState) {
        super.render(current)
        val congestionColor = getCongestionColor(current.congestion)
        binding.apply {
            imageViewBack.setColorFilter(congestionColor)
            textViewLocationName.setTextColor(congestionColor)
            constraintLayoutDetailInfo.setBackgroundColor(congestionColor)

            textViewLocationName.text = current.locationName
            textViewCongestion.text = getString(
                R.string.fragment_location_detail_now,
                getCongestionText(current.congestion),
            )
            textViewCongestion.changeTextStyle(
                listOf(getCongestionText(current.congestion)),
                com.onewx2m.design_system.R.style.Caption_1_white,
            )
            setCongestionMaybeTextView(current.congestion, current.mayRelaxAt, current.mayBuzzAt)
        }

        if (current.isInitializingDetailInfo.not() && current.isInitializingViewPagerData.not() && binding.constraintLayoutDetail.isVisible.not()) {
            binding.constraintLayoutDetail.setVisibleWithAnimation()
            binding.lottieLoading.setGoneWithAnimation()
        }

        if (binding.constraintLayoutDetail.isVisible && current.isInitializingViewPagerData.not()) {
            initViewPagerAndTabLayout(current.congestion.name)
        }
    }

    private fun setCongestionMaybeTextView(
        congestion: Congestion,
        mayRelaxAt: Int?,
        mayBuzzAt: Int?,
    ) {
        when {
            congestion == RELAX && mayBuzzAt != null -> {
                changeTextViewMayBeWithTime(
                    R.string.fragment_location_detail_may_be_buzz,
                    mayBuzzAt,
                )
            }

            congestion != RELAX && mayRelaxAt != null -> {
                changeTextViewMayBeWithTime(
                    R.string.fragment_location_detail_may_be_relax,
                    mayRelaxAt,
                )
            }

            congestion != RELAX && mayRelaxAt == null ->
                binding.textViewMayBe.text =
                    getString(R.string.fragment_location_detail_may_be_not_relax)

            else ->
                binding.textViewMayBe.text =
                    getString(R.string.fragment_location_detail_may_be_not_buzz)
        }
    }

    private fun changeTextViewMayBeWithTime(@StringRes stringId: Int, time: Int) {
        val timeText = TimeFormatter.getahLocaleKorean(time)
        binding.textViewMayBe.text =
            getString(stringId, timeText)
        binding.textViewMayBe.changeTextStyle(
            listOf(timeText),
            com.onewx2m.design_system.R.style.Caption_1_white,
        )
    }

    override fun handleSideEffect(sideEffect: LocationDetailSideEffect) {
        super.handleSideEffect(sideEffect)
        when (sideEffect) {
            LocationDetailSideEffect.GoToLoginFragment -> goToLoginFragment()
            is LocationDetailSideEffect.ShowErrorToast -> ErrorToast.make(
                binding.root,
                sideEffect.msg,
            ).show()

            LocationDetailSideEffect.PopBackStack -> findNavController().popBackStack()
            is LocationDetailSideEffect.InitViewPagerAndTabLayout -> initViewPagerAndTabLayout(
                sideEffect.congestion,
            )
        }
    }

    private fun goToLoginFragment() {
        val (request, navOptions) = DeepLinkUtil.getLoginRequestAndOption(
            requireContext(),
            findNavController().graph.id,
            true,
        )
        findNavController().navigate(request, navOptions)
    }

    private fun getCongestionColor(congestion: Congestion) = when (congestion) {
        RELAX -> ContextCompat.getColor(requireContext(), com.onewx2m.design_system.R.color.mint)
        NORMAL -> ContextCompat.getColor(requireContext(), com.onewx2m.design_system.R.color.blue)
        BUZZING -> ContextCompat.getColor(requireContext(), com.onewx2m.design_system.R.color.pink)
    }

    private fun getCongestionText(congestion: Congestion) = when (congestion) {
        RELAX -> getString(R.string.fragment_location_congestion_text_relax)
        NORMAL -> getString(R.string.fragment_location_congestion_text_normal)
        BUZZING -> getString(R.string.fragment_location_congestion_text_buzz)
    }

    private fun initViewPagerAndTabLayout(congestion: String) {
        pagerAdapter = LocationDetailFragmentStateAdapter(
            this,
            congestion,
            navArgs.locationId,
        ) { viewModel.finishViewPagerDataInit() }

        binding.viewPager2.apply {
            isUserInputEnabled = false
            adapter = pagerAdapter
        }

        TabLayoutMediator(binding.tabLayout, binding.viewPager2) { tab, position ->
            tab.text = getTabTitleString(LocationDetailViewPagerType.getType(position))
            tab.customView = getTabView(LocationDetailViewPagerType.getType(position))
        }.attach()
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
