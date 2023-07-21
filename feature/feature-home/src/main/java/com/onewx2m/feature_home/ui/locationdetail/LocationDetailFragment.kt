package com.onewx2m.feature_home.ui.locationdetail

import androidx.fragment.app.viewModels
import com.onewx2m.feature_home.databinding.FragmentLocationDetailBinding
import com.onewx2m.mvi.MviFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LocationDetailFragment :
    MviFragment<FragmentLocationDetailBinding, LocationDetailViewState, LocationDetailEvent, LocationDetailSideEffect, LocationDetailViewModel>(
        FragmentLocationDetailBinding::inflate,
    ) {
    override val viewModel: LocationDetailViewModel by viewModels()

    override fun initView() {
        //
    }
}
