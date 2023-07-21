package com.onewx2m.feature_home.ui.locationdetail

import androidx.lifecycle.viewModelScope
import com.onewx2m.domain.collectOutcome
import com.onewx2m.domain.usecase.GetBuzzzzingLocationDetailUseCase
import com.onewx2m.mvi.MviViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LocationDetailViewModel @Inject constructor(
    private val getBuzzzzingLocationDetailUseCase: GetBuzzzzingLocationDetailUseCase,
) :
    MviViewModel<LocationDetailViewState, LocationDetailEvent, LocationDetailSideEffect>(
        LocationDetailViewState(),
    ) {

    fun getLocationDetailUseCase(locationId: Int) = viewModelScope.launch {
        getBuzzzzingLocationDetailUseCase(locationId).collectOutcome()
    }

    override fun reduceState(
        current: LocationDetailViewState,
        event: LocationDetailEvent,
    ): LocationDetailViewState {
        TODO("Not yet implemented")
    }
}
