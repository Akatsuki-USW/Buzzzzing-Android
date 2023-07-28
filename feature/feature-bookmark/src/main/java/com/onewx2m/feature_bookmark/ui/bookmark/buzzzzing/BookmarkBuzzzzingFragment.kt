package com.onewx2m.feature_bookmark.ui.bookmark.buzzzzing

import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.onewx2m.core_ui.extensions.infiniteScrolls
import com.onewx2m.core_ui.extensions.navigateActionWithDefaultAnim
import com.onewx2m.core_ui.extensions.setGoneWithAnimation
import com.onewx2m.core_ui.extensions.setVisibleWithAnimation
import com.onewx2m.core_ui.util.DeepLinkUtil
import com.onewx2m.design_system.components.recyclerview.buzzzzingmedium.BuzzzzingMediumAdapter
import com.onewx2m.design_system.components.toast.ErrorToast
import com.onewx2m.feature_bookmark.databinding.FragmentBookmarkBuzzzzingBinding
import com.onewx2m.mvi.MviFragment
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class BookmarkBuzzzzingFragment :
    MviFragment<FragmentBookmarkBuzzzzingBinding, BookmarkBuzzzzingViewState, BookmarkBuzzzzingEvent, BookmarkBuzzzzingSideEffect, BookmarkBuzzzzingViewModel>(
        FragmentBookmarkBuzzzzingBinding::inflate,
    ) {
    override val viewModel: BookmarkBuzzzzingViewModel by viewModels()

    private val buzzzzingMediumAdapter: BuzzzzingMediumAdapter by lazy {
        BuzzzzingMediumAdapter(
            onBookmarkClick = {
                viewModel.bookmark(it)
            },
            onItemClick = {
                viewModel.goToLocationDetailFragment(it)
            },
        )
    }

    override fun onStart() {
        super.onStart()
        viewModel.getBuzzzzingLocationBookmarked(true)
    }

    override fun initView() {
        binding.recyclerView.apply {
            adapter = buzzzzingMediumAdapter
            itemAnimator = null
            layoutManager = LinearLayoutManager(requireContext())
            infiniteScrolls {
                viewModel.getBuzzzzingLocationBookmarked()
            }
        }
    }

    override fun render(current: BookmarkBuzzzzingViewState) {
        super.render(current)

        buzzzzingMediumAdapter.submitList(current.buzzzzingMediumItem)
        if (current.isInitializing.not() && binding.recyclerView.isVisible.not()) {
            binding.lottieLoading.setGoneWithAnimation()
            binding.recyclerView.setVisibleWithAnimation()
        }
    }

    override fun handleSideEffect(sideEffect: BookmarkBuzzzzingSideEffect) {
        super.handleSideEffect(sideEffect)

        when (sideEffect) {
            is BookmarkBuzzzzingSideEffect.ShowErrorToast -> ErrorToast.make(binding.root, sideEffect.msg).show()
            BookmarkBuzzzzingSideEffect.GoToLoginFragment -> goToLoginFragment()
            is BookmarkBuzzzzingSideEffect.GoToLocationDetailFragment -> goToLocationDetailFragment(sideEffect.locationId)
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

    private fun goToLocationDetailFragment(locationId: Int) {
        //val action = HomeFragmentDirections.actionHomeToLocationDetail(locationId)
        //findNavController().navigateActionWithDefaultAnim(action)
    }
}
