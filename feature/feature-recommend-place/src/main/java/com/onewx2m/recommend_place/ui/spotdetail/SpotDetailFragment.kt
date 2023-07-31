package com.onewx2m.recommend_place.ui.spotdetail

import android.os.Bundle
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.NavArgs
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import com.onewx2m.core_ui.extensions.infiniteScrolls
import com.onewx2m.core_ui.extensions.setGoneWithAnimation
import com.onewx2m.core_ui.extensions.setVisibleWithAnimation
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
            onBookmarkClick = {
            },
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel.getSpotDetail(navArgs.spotId)
    }

    override fun initView() {
        val concatAdapter = ConcatAdapter(
            spotDetailContentAdapter,
        )

        binding.recyclerView.apply {
            adapter = concatAdapter
            itemAnimator = null
            layoutManager = LinearLayoutManager(requireContext())
            infiniteScrolls {
            }
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
    }

    override fun handleSideEffect(sideEffect: SpotDetailSideEffect) {
        super.handleSideEffect(sideEffect)
    }
}
