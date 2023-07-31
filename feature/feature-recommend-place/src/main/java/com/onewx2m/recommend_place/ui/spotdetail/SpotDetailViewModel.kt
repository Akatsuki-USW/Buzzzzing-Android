package com.onewx2m.recommend_place.ui.spotdetail

import com.onewx2m.mvi.MviViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SpotDetailViewModel @Inject constructor() : MviViewModel<SpotDetailViewState, SpotDetailEvent, SpotDetailSideEffect>(
    SpotDetailViewState(),
) {

    override fun reduceState(
        current: SpotDetailViewState,
        event: SpotDetailEvent,
    ): SpotDetailViewState {
        return current
    }
}
