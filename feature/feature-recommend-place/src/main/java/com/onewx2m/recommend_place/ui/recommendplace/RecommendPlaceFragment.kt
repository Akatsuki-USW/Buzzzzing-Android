package com.onewx2m.recommend_place.ui.recommendplace

import android.os.Bundle
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.onewx2m.core_ui.extensions.infiniteScrolls
import com.onewx2m.core_ui.util.DeepLinkUtil
import com.onewx2m.design_system.components.recyclerview.spot.SpotAdapter
import com.onewx2m.design_system.components.recyclerview.spotcategoryselector.SpotCategorySelectorAdapter
import com.onewx2m.design_system.components.toast.ErrorToast
import com.onewx2m.design_system.enum.Congestion
import com.onewx2m.mvi.MviFragment
import com.onewx2m.recommend_place.R
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
        SpotAdapter(congestion, onItemClick = {}, onBookmarkClick = { viewModel.bookmark(it) })
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel.initData()
    }

    override fun initView() {
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

            binding.recyclerViewCategory.adapter = spotCategorySelectorAdapter
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
        }
    }

    private fun goToLoginFragment() {
        val (request, navOptions) = DeepLinkUtil.getLoginRequestAndOption(
            requireContext(),
            R.id.recommendPlaceFragment,
            true,
        )
        findNavController().navigate(request, navOptions)
    }
}
