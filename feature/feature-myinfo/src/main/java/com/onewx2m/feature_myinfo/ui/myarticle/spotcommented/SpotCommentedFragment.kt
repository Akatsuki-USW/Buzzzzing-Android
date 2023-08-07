package com.onewx2m.feature_myinfo.ui.myarticle.spotcommented

import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.onewx2m.core_ui.extensions.infiniteScrolls
import com.onewx2m.core_ui.extensions.setGoneWithAnimation
import com.onewx2m.core_ui.extensions.setVisibleWithAnimation
import com.onewx2m.core_ui.util.DeepLinkUtil
import com.onewx2m.design_system.components.recyclerview.spot.SpotAdapter
import com.onewx2m.design_system.components.toast.ErrorToast
import com.onewx2m.design_system.enum.Congestion
import com.onewx2m.feature_myinfo.databinding.FragmentSpotCommentedBinding
import com.onewx2m.mvi.MviFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SpotCommentedFragment :
    MviFragment<FragmentSpotCommentedBinding, SpotCommentedViewState, SpotCommentedEvent, SpotCommentedSideEffect, SpotCommentedViewModel>(
        FragmentSpotCommentedBinding::inflate,
    ) {
    override val viewModel: SpotCommentedViewModel by viewModels({ requireParentFragment() })

    private val spotAdapter: SpotAdapter by lazy {
        SpotAdapter(
            congestion = Congestion.NORMAL.name,
            onItemClick = { viewModel.goToSpotDetail(it) },
            onBookmarkClick = { viewModel.bookmark(it) },
        )
    }

    override fun initView() {
        binding.recyclerView.apply {
            adapter = spotAdapter
            itemAnimator = null
            layoutManager = LinearLayoutManager(requireContext())
            infiniteScrolls {
                viewModel.getSpotCommented()
            }
        }
    }

    override fun render(current: SpotCommentedViewState) {
        super.render(current)

        spotAdapter.submitList(current.spotList)
        if (current.isInitializing.not() && binding.recyclerView.isVisible.not()) {
            binding.lottieLoading.setGoneWithAnimation()
            binding.recyclerView.setVisibleWithAnimation()
        }
    }

    override fun handleSideEffect(sideEffect: SpotCommentedSideEffect) {
        super.handleSideEffect(sideEffect)

        when (sideEffect) {
            is SpotCommentedSideEffect.ShowErrorToast -> ErrorToast.make(
                binding.root,
                sideEffect.msg,
            ).show()

            SpotCommentedSideEffect.GoToLoginFragment -> goToLoginFragment()
            is SpotCommentedSideEffect.GoToSpotDetailFragment -> goToSpotDetailFragment(sideEffect.spotId)
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

    private fun goToSpotDetailFragment(spotId: Int) {
        val (request, navOptions) = DeepLinkUtil.getSpotDetailRequestAndOption(
            requireContext(),
            spotId,
        )
        findNavController().navigate(request, navOptions)
    }
}
