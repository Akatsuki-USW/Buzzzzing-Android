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
import com.onewx2m.design_system.components.recyclerview.spotcomment.children.SpotChildrenCommentItem
import com.onewx2m.design_system.components.recyclerview.spotcomment.parent.SpotParentCommentAdapter
import com.onewx2m.design_system.components.recyclerview.spotcomment.parent.SpotParentCommentItem
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
            onParentMeatBallClick = viewModel::showParentCommentPowerMenu,
            onChildMeatBallClick = viewModel::showChildrenCommentPowerMenu,
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
            is SpotDetailSideEffect.ShowContentPowerMenu -> showContentPowerMenu()
            is SpotDetailSideEffect.ShowErrorToast -> ErrorToast.make(binding.root, sideEffect.msg)
                .show()

            SpotDetailSideEffect.GoToPrevPage -> findNavController().popBackStack()
            is SpotDetailSideEffect.ShowChildCommentPowerMenu -> showChildCommentPowerMenu(sideEffect.view, sideEffect.item)
            is SpotDetailSideEffect.ShowParentCommentPowerMenu -> showParentCommentPowerMenu(sideEffect.view, sideEffect.item)
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

    private fun showParentCommentPowerMenu(view: View, item: SpotParentCommentItem) {
        view.showPowerMenu(
            0,
            0,
            viewLifecycleOwner,
            viewModel.getParentCommentPowerMenuList(item.isAuthor),
        ) { _, menu ->
            when (PowerMenuType.typeOf(menu)) {
                PowerMenuType.EDIT -> Unit
                PowerMenuType.DELETE -> Unit
                PowerMenuType.REPORT -> Unit
                PowerMenuType.BLOCK -> Unit
                PowerMenuType.REPLY -> Unit
            }
        }
    }

    private fun showChildCommentPowerMenu(view: View, item: SpotChildrenCommentItem) {
        view.showPowerMenu(
            0,
            0,
            viewLifecycleOwner,
            viewModel.getChildrenCommentPowerMenuList(item.isAuthor),
        ) { _, menu ->
            when (PowerMenuType.typeOf(menu)) {
                PowerMenuType.EDIT -> Unit
                PowerMenuType.DELETE -> Unit
                PowerMenuType.REPORT -> Unit
                PowerMenuType.BLOCK -> Unit
                PowerMenuType.REPLY -> Unit
            }
        }
    }
}
