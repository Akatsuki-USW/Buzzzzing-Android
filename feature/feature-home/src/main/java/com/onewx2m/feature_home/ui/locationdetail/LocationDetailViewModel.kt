package com.onewx2m.feature_home.ui.locationdetail

import com.onewx2m.mvi.MviViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LocationDetailViewModel @Inject constructor() :
    MviViewModel<LocationDetailViewState, LocationDetailEvent, LocationDetailSideEffect>(LocationDetailViewState()) {
    override fun reduceState(
        current: LocationDetailViewState,
        event: LocationDetailEvent,
    ): LocationDetailViewState {
        TODO("Not yet implemented")
    }
}
