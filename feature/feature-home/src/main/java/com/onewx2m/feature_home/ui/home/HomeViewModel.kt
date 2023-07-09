package com.onewx2m.feature_home.ui.home

import com.onewx2m.mvi.MviViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor() : MviViewModel<HomeViewState, HomeEvent, HomeSideEffect>(
    HomeViewState(),
) {
    override fun reduceState(current: HomeViewState, event: HomeEvent): HomeViewState {
        TODO("Not yet implemented")
    }
}
