package com.onewx2m.feature_home.ui.home

import android.os.Bundle
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import com.onewx2m.core_ui.extensions.infiniteScrolls
import com.onewx2m.core_ui.util.DeepLinkUtil
import com.onewx2m.design_system.components.recyclerview.buzzzzingmedium.BuzzzzingMediumAdapter
import com.onewx2m.design_system.components.toast.ErrorToast
import com.onewx2m.feature_home.R
import com.onewx2m.feature_home.databinding.FragmentHomeBinding
import com.onewx2m.feature_home.ui.home.adapter.HomeBuzzzzingSmallAdapter
import com.onewx2m.feature_home.ui.home.adapter.HomeHeaderAdapter
import com.onewx2m.feature_home.ui.home.adapter.HomeSearchAdapter
import com.onewx2m.feature_home.ui.home.bottomsheet.SimpleSelectorBottomSheet
import com.onewx2m.mvi.MviFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment :
    MviFragment<FragmentHomeBinding, HomeViewState, HomeEvent, HomeSideEffect, HomeViewModel>(
        FragmentHomeBinding::inflate,
    ) {
    override val viewModel: HomeViewModel by viewModels()

    private val homeHeaderAdapter: HomeHeaderAdapter by lazy {
        HomeHeaderAdapter()
    }

    private val homeBuzzzzingSmallAdapter: HomeBuzzzzingSmallAdapter by lazy {
        HomeBuzzzzingSmallAdapter(
            onBookmarkClick = {
                viewModel.bookmark(it)
            },
            onItemClick = {
                ErrorToast.make(binding.root, "$it 아이템 클릭").show()
            },
        )
    }

    private val homeSearchAdapter: HomeSearchAdapter by lazy {
        HomeSearchAdapter(
            onSearch = { viewModel.onSearch(it) },
            onLocationFilterClick = { viewModel.postShowLocationBottomSheetSideEffect() },
            onCongestionFilterClick = { viewModel.postShowCongestionBottomSheetSideEffect() },
            onClear = { viewModel.onSearchClear() },
        )
    }

    private val buzzzzingMediumAdapter: BuzzzzingMediumAdapter by lazy {
        BuzzzzingMediumAdapter(
            onBookmarkClick = {
                viewModel.bookmark(it)
            },
            onItemClick = {
                ErrorToast.make(binding.root, "$it 아이템 클릭").show()
            },
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel.initData()
    }

    override fun initView() {
        val concatAdapter = ConcatAdapter(
            homeHeaderAdapter,
            homeBuzzzzingSmallAdapter,
            homeSearchAdapter,
            buzzzzingMediumAdapter,
        )

        binding.recyclerView.apply {
            adapter = concatAdapter
            layoutManager = LinearLayoutManager(requireContext())
            infiniteScrolls {
                viewModel.getBuzzzzingLocation()
            }
        }
    }

    override fun render(current: HomeViewState) {
        super.render(current)

        buzzzzingMediumAdapter.submitList(current.buzzzzingMediumItem)
        homeSearchAdapter.locationSpinnerText = current.locationFilter
        homeSearchAdapter.congestionLevelSpinnerText = current.congestionFilter
        homeSearchAdapter.keyword = current.keyword
        homeSearchAdapter.notifyItemChanged(0)

        homeBuzzzzingSmallAdapter.submitList(current.buzzzzingSmallItem)
    }

    override fun handleSideEffect(sideEffect: HomeSideEffect) {
        super.handleSideEffect(sideEffect)

        when (sideEffect) {
            HomeSideEffect.ShowCongestionBottomSheet -> showCongestionBottomSheet()
            HomeSideEffect.ShowLocationBottomSheet -> showLocationBottomSheet()
            is HomeSideEffect.ShowErrorToast -> ErrorToast.make(binding.root, sideEffect.msg).show()
            HomeSideEffect.GoToLoginFragment -> goToLoginFragment()
        }
    }

    private fun showLocationBottomSheet() {
        SimpleSelectorBottomSheet.newInstance(
            viewModel.state.value.locationFilter,
            viewModel.locationCategoryValues,
        ) {
            viewModel.onClickLocationFilterItemClick(it)
        }.show(parentFragmentManager, "")
    }

    private fun showCongestionBottomSheet() {
        SimpleSelectorBottomSheet.newInstance(
            viewModel.state.value.congestionFilter,
            viewModel.congestionLevelCategoryValues,
        ) {
            viewModel.onClickCongestionFilterItemClick(it)
        }.show(parentFragmentManager, "")
    }

    private fun goToLoginFragment() {
        val (request, navOptions) = DeepLinkUtil.getLoginRequestAndOption(requireContext(), R.id.homeFragment, true)
        findNavController().navigate(request, navOptions)
    }
}
