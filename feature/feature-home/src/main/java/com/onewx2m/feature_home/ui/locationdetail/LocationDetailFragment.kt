package com.onewx2m.feature_home.ui.locationdetail

import android.os.Bundle
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.onewx2m.feature_home.databinding.FragmentLocationDetailBinding
import com.onewx2m.mvi.MviFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LocationDetailFragment :
    MviFragment<FragmentLocationDetailBinding, LocationDetailViewState, LocationDetailEvent, LocationDetailSideEffect, LocationDetailViewModel>(
        FragmentLocationDetailBinding::inflate,
    ) {
    override val viewModel: LocationDetailViewModel by viewModels()
    private val navArgs: LocationDetailFragmentArgs by navArgs()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.getLocationDetailUseCase(navArgs.locationId)
    }

    override fun initView() {
        //
    }
}
