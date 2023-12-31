package com.onewx2m.recommend_place.ui.recommendplace

import android.os.Bundle
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.onewx2m.core_ui.extensions.infiniteScrolls
import com.onewx2m.core_ui.extensions.onThrottleClick
import com.onewx2m.core_ui.model.WRITE_CONTENT_KEY
import com.onewx2m.core_ui.model.WriteContent
import com.onewx2m.core_ui.util.DeepLinkUtil
import com.onewx2m.design_system.components.recyclerview.spot.SpotAdapter
import com.onewx2m.design_system.components.recyclerview.spotcategoryselector.SpotCategorySelectorAdapter
import com.onewx2m.design_system.components.toast.ErrorToast
import com.onewx2m.design_system.enum.Congestion
import com.onewx2m.mvi.MviFragment
import com.onewx2m.recommend_place.databinding.FragmentRecommendPlaceBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RecommendPlaceFragment :
    MviFragment<FragmentRecommendPlaceBinding, RecommendPlaceViewState, RecommendPlaceEvent, RecommendPlaceSideEffect, RecommendPlaceViewModel>(
        FragmentRecommendPlaceBinding::inflate,
    ) {
    private val congestion = Congestion.NORMAL.name

    override val viewModel: RecommendPlaceViewModel by viewModels()

    private var spotCategorySelectorAdapter: SpotCategorySelectorAdapter? = null
    private val spotAdapter: SpotAdapter by lazy {
        SpotAdapter(
            congestion,
            onItemClick = { viewModel.goToSpotDetail(it) },
            onBookmarkClick = { viewModel.bookmark(it) },
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel.initData()
    }

    override fun initView() {
        binding.buttonWrite.onThrottleClick {
            viewModel.postGoToWriteFragmentSideEffect()
        }

        binding.recyclerViewCategory.apply {
            layoutManager = LinearLayoutManager(requireContext()).apply {
                orientation = LinearLayoutManager.HORIZONTAL
            }
        }

        binding.recyclerViewSpot.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = spotAdapter
            itemAnimator = null
            infiniteScrolls {
                viewModel.getSpotList()
            }
        }

        findNavController().currentBackStackEntry?.savedStateHandle?.getLiveData<WriteContent>(
            WRITE_CONTENT_KEY,
        )
            ?.observe(viewLifecycleOwner) { data ->
                viewModel.updateListItem(data)
            }
    }

    override fun render(current: RecommendPlaceViewState) {
        super.render(current)

        if (spotCategorySelectorAdapter == null && current.spotCategoryItems.isNotEmpty() && current.selectedSpotCategoryItem != null) {
            spotCategorySelectorAdapter = SpotCategorySelectorAdapter(
                Congestion.valueOf(congestion),
                current.spotCategoryItems,
                current.selectedSpotCategoryItem,
            ) {
                viewModel.updateCategoryId(it.id)
                viewModel.getSpotList(true)
            }
        }

        if (spotCategorySelectorAdapter != null && binding.recyclerViewCategory.adapter == null) {
            binding.recyclerViewCategory.adapter =
                spotCategorySelectorAdapter
        }

        spotAdapter.submitList(current.spotList)
    }

    override fun handleSideEffect(sideEffect: RecommendPlaceSideEffect) {
        super.handleSideEffect(sideEffect)

        when (sideEffect) {
            is RecommendPlaceSideEffect.ShowErrorToast -> ErrorToast.make(
                binding.root,
                sideEffect.msg,
            ).show()

            RecommendPlaceSideEffect.GoToLoginFragment -> goToLoginFragment()
            is RecommendPlaceSideEffect.GoToWriteFragment -> goToWriteFragment()
            is RecommendPlaceSideEffect.GoToSpotDetailFragment -> goToSpotDetailFragment(sideEffect.spotId)
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
        val (request, navOptions) = DeepLinkUtil.getWriteRequestAndOption(requireContext())
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
