package com.onewx2m.feature_home.ui.locationdetail.spot

import android.os.Bundle
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.onewx2m.design_system.components.recyclerview.spotcategoryselector.SpotCategorySelectorAdapter
import com.onewx2m.design_system.components.toast.ErrorToast
import com.onewx2m.design_system.enum.Congestion
import com.onewx2m.feature_home.databinding.FragmentSpotBinding
import com.onewx2m.mvi.MviFragment
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class SpotFragment :
    MviFragment<FragmentSpotBinding, SpotViewState, SpotEvent, SpotSideEffect, SpotViewModel>(
        FragmentSpotBinding::inflate,
    ) {
    private val congestion: String
        get() = arguments?.getString(CONGESTION) ?: ""

    private val locationId: Int
        get() = arguments?.getInt(LOCATION_ID) ?: -1

    companion object {
        private const val CONGESTION = "congestion"
        private const val LOCATION_ID = "locationId"

        @JvmStatic
        fun newInstance(
            congestion: String,
            locationId: Int,
        ): SpotFragment {
            return SpotFragment().apply {
                arguments = Bundle().apply {
                    putString(CONGESTION, congestion)
                    putInt(LOCATION_ID, locationId)
                }
            }
        }
    }

    override val viewModel: SpotViewModel by viewModels()

    private var spotCategorySelectorAdapter: SpotCategorySelectorAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel.initData(locationId)
    }

    override fun initView() {
        binding.recyclerViewCategory.apply {
            layoutManager = LinearLayoutManager(requireContext()).apply {
                orientation = LinearLayoutManager.HORIZONTAL
            }
        }

        binding.recyclerViewSpot.initAdapter(
            congestion,
            onItemClick = {},
            onBookmarkClick = { viewModel.bookmark(it) },
            infiniteScrolls = { viewModel.getSpotList(locationId) },
        )
    }

    override fun render(current: SpotViewState) {
        super.render(current)

        if (spotCategorySelectorAdapter == null && current.spotCategoryItems.isNotEmpty() && current.selectedSpotCategoryItem != null) {
            spotCategorySelectorAdapter = SpotCategorySelectorAdapter(
                Congestion.valueOf(congestion),
                current.spotCategoryItems,
                current.selectedSpotCategoryItem,
            ) {
                viewModel.updateCategoryId(it.id)
                viewModel.getSpotList(locationId, true)
            }

            binding.recyclerViewCategory.adapter = spotCategorySelectorAdapter
        }
        Timber.tag("테스트").d("${current.spotList.map { it.id }}")
        binding.recyclerViewSpot.submitList(current.spotList)
    }

    override fun handleSideEffect(sideEffect: SpotSideEffect) {
        super.handleSideEffect(sideEffect)

        when (sideEffect) {
            is SpotSideEffect.ShowErrorToast -> ErrorToast.make(binding.root, sideEffect.msg).show()
            SpotSideEffect.GoToLoginFragment -> TODO()
        }
    }
}
