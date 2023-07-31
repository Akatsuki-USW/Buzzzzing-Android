package com.onewx2m.recommend_place.ui.spotdetail

import androidx.fragment.app.viewModels
import com.onewx2m.mvi.MviFragment
import com.onewx2m.recommend_place.databinding.FragmentSpotDetailBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SpotDetailFragment :
    MviFragment<FragmentSpotDetailBinding, SpotDetailViewState, SpotDetailEvent, SpotDetailSideEffect, SpotDetailViewModel>(
        FragmentSpotDetailBinding::inflate,
    ) {
    override val viewModel: SpotDetailViewModel by viewModels()

    override fun initView() {

    }
}
