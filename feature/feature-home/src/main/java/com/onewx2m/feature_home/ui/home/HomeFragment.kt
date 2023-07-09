package com.onewx2m.feature_home.ui.home

import androidx.fragment.app.viewModels
import com.onewx2m.feature_home.databinding.FragmentHomeBinding
import com.onewx2m.mvi.MviFragment

class HomeFragment :
    MviFragment<FragmentHomeBinding, HomeViewState, HomeEvent, HomeSideEffect, HomeViewModel>(
        FragmentHomeBinding::inflate,
    ) {
    override val viewModel: HomeViewModel by viewModels()

    override fun initView() {
    }
}
