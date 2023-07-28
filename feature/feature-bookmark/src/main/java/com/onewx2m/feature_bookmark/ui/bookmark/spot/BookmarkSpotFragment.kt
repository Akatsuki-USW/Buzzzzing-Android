package com.onewx2m.feature_bookmark.ui.bookmark.spot

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
import com.onewx2m.feature_bookmark.databinding.FragmentBookmarkSpotBinding
import com.onewx2m.mvi.MviFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BookmarkSpotFragment :
    MviFragment<FragmentBookmarkSpotBinding, BookmarkSpotViewState, BookmarkSpotEvent, BookmarkSpotSideEffect, BookmarkSpotViewModel>(
        FragmentBookmarkSpotBinding::inflate,
    ) {
    override val viewModel: BookmarkSpotViewModel by viewModels()

    private val spotAdapter: SpotAdapter by lazy {
        SpotAdapter(
            congestion = Congestion.NORMAL.name,
            onItemClick = {},
            onBookmarkClick = { viewModel.bookmark(it) },
        )
    }

    override fun initView() {
        viewModel.getSpotBookmarked(true)

        binding.recyclerView.apply {
            adapter = spotAdapter
            itemAnimator = null
            layoutManager = LinearLayoutManager(requireContext())
            infiniteScrolls {
                viewModel.getSpotBookmarked()
            }
        }
    }

    override fun render(current: BookmarkSpotViewState) {
        super.render(current)

        spotAdapter.submitList(current.spotList)
        if (current.isInitializing.not() && binding.recyclerView.isVisible.not()) {
            binding.lottieLoading.setGoneWithAnimation()
            binding.recyclerView.setVisibleWithAnimation()
        }
    }

    override fun handleSideEffect(sideEffect: BookmarkSpotSideEffect) {
        super.handleSideEffect(sideEffect)

        when (sideEffect) {
            is BookmarkSpotSideEffect.ShowErrorToast -> ErrorToast.make(
                binding.root,
                sideEffect.msg,
            ).show()

            BookmarkSpotSideEffect.GoToLoginFragment -> goToLoginFragment()
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
}
