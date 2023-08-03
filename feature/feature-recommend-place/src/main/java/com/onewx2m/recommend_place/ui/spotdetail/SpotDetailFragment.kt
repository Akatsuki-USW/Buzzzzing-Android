package com.onewx2m.recommend_place.ui.spotdetail

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import com.onewx2m.core_ui.extensions.infiniteScrolls
import com.onewx2m.core_ui.extensions.onThrottleClick
import com.onewx2m.core_ui.extensions.setGoneWithAnimation
import com.onewx2m.core_ui.extensions.setVisibleWithAnimation
import com.onewx2m.core_ui.util.DeepLinkUtil
import com.onewx2m.design_system.components.powermenu.PowerMenuType
import com.onewx2m.design_system.components.powermenu.showPowerMenu
import com.onewx2m.design_system.components.recyclerview.spotcomment.parent.SpotParentCommentAdapter
import com.onewx2m.design_system.components.toast.ErrorToast
import com.onewx2m.mvi.MviFragment
import com.onewx2m.recommend_place.databinding.FragmentSpotDetailBinding
import com.onewx2m.recommend_place.ui.spotdetail.adapter.SpotDetailContentAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SpotDetailFragment :
    MviFragment<FragmentSpotDetailBinding, SpotDetailViewState, SpotDetailEvent, SpotDetailSideEffect, SpotDetailViewModel>(
        FragmentSpotDetailBinding::inflate,
    ) {
    override val viewModel: SpotDetailViewModel by viewModels()
    private val navArgs by navArgs<SpotDetailFragmentArgs>()

    private val spotDetailContentAdapter: SpotDetailContentAdapter by lazy {
        SpotDetailContentAdapter(
            onBookmarkClick = viewModel::bookmark,
        )
    }

    private val spotParentCommentAdapter: SpotParentCommentAdapter by lazy {
        SpotParentCommentAdapter(
            onParentMeatBallClick = { view, item -> },
            onChildMeatBallClick = { view, item -> },
            onMoreClick = { item -> viewModel.getChildrenComments(item.id) },
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel.initData(navArgs.spotId)
    }

    override fun initView() {
        val concatAdapter = ConcatAdapter(
            spotDetailContentAdapter,
            spotParentCommentAdapter,
        )

        binding.recyclerView.apply {
            adapter = concatAdapter
            layoutManager = LinearLayoutManager(requireContext())
            infiniteScrolls {
                viewModel.getSpotParentCommentList(navArgs.spotId)
            }
        }

        binding.imageViewArticleMeatBall.onThrottleClick {
            viewModel.showContentPowerMenu()
        }

        binding.imageViewBack.onThrottleClick {
            viewModel.goToPrevPage()
        }
    }

    override fun render(current: SpotDetailViewState) {
        super.render(current)

        with(binding) {
            if (lottieLoading.isVisible && current.isLoadingLottieVisible.not()) {
                recyclerView.setVisibleWithAnimation()
                lottieLoading.setGoneWithAnimation()
            }
        }

        spotDetailContentAdapter.setData(
            current.spotDetailContent,
        )

        spotParentCommentAdapter.submitList(current.spotCommentList)

        binding.lottieLoadingSmall.visibility =
            if (current.isSmallLottieVisible) View.VISIBLE else View.GONE
    }

    override fun handleSideEffect(sideEffect: SpotDetailSideEffect) {
        super.handleSideEffect(sideEffect)

        when (sideEffect) {
            SpotDetailSideEffect.GoToLoginFragment -> goToLoginFragment()
            SpotDetailSideEffect.GoToWriteFragment -> goToWriteFragment()
            SpotDetailSideEffect.ShowContentPowerMenu -> showContentPowerMenu()
            is SpotDetailSideEffect.ShowErrorToast -> ErrorToast.make(binding.root, sideEffect.msg)
                .show()

            SpotDetailSideEffect.GoToPrevPage -> findNavController().popBackStack()
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

    private fun goToWriteFragment() {
        // TODO WriteContent 넣어야함.
        val (request, navOptions) = DeepLinkUtil.getWriteRequestAndOption(requireContext())
        findNavController().navigate(request, navOptions)
    }

    private fun showContentPowerMenu() {
        binding.imageViewArticleMeatBall.showPowerMenu(
            0,
            0,
            viewLifecycleOwner,
            viewModel.contentPowerMenuList,
        ) { _, item ->
            when (PowerMenuType.typeOf(item)) {
                PowerMenuType.EDIT -> viewModel.goToWriteFragment()
                PowerMenuType.DELETE -> Unit
                PowerMenuType.REPORT -> Unit
                PowerMenuType.BLOCK -> Unit
                PowerMenuType.REPLY -> Unit
            }
        }
    }
}
