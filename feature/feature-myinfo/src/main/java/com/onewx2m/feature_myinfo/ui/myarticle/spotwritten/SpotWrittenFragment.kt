package com.onewx2m.feature_myinfo.ui.myarticle.spotwritten

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
import com.onewx2m.feature_myinfo.databinding.FragmentSpotWrittenBinding
import com.onewx2m.mvi.MviFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SpotWrittenFragment :
    MviFragment<FragmentSpotWrittenBinding, SpotWrittenViewState, SpotWrittenEvent, SpotWrittenSideEffect, SpotWrittenViewModel>(
        FragmentSpotWrittenBinding::inflate,
    ) {
    override val viewModel: SpotWrittenViewModel by viewModels({ requireParentFragment() })

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
                viewModel.getSpotWritten()
            }
        }
    }

    override fun render(current: SpotWrittenViewState) {
        super.render(current)

        spotAdapter.submitList(current.spotList)
        if (current.isInitializing.not() && binding.recyclerView.isVisible.not()) {
            binding.lottieLoading.setGoneWithAnimation()
            binding.recyclerView.setVisibleWithAnimation()
        }
    }

    override fun handleSideEffect(sideEffect: SpotWrittenSideEffect) {
        super.handleSideEffect(sideEffect)

        when (sideEffect) {
            is SpotWrittenSideEffect.ShowErrorToast -> ErrorToast.make(
                binding.root,
                sideEffect.msg,
            ).show()

            SpotWrittenSideEffect.GoToLoginFragment -> goToLoginFragment()
            is SpotWrittenSideEffect.GoToSpotDetailFragment -> goToSpotDetailFragment(sideEffect.spotId)
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
