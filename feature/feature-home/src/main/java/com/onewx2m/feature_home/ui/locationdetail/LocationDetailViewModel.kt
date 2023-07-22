package com.onewx2m.feature_home.ui.locationdetail

import androidx.lifecycle.viewModelScope
import com.onewx2m.design_system.enum.Congestion
import com.onewx2m.domain.Outcome
import com.onewx2m.domain.collectOutcome
import com.onewx2m.domain.exception.common.CommonException
import com.onewx2m.domain.model.BuzzzzingLocationDetailInfo
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
        getBuzzzzingLocationDetailUseCase(locationId).collectOutcome(::handleLocationDetailSuccess) {
            handleError(it.error)
        }
    }

    private fun handleLocationDetailSuccess(outcome: Outcome.Success<BuzzzzingLocationDetailInfo>) {
        postEvent(
            LocationDetailEvent.UpdateInitData(
                name = outcome.data.name,
                congestion = Congestion.valueOf(outcome.data.congestionSymbol),
                mayBuzzAt = outcome.data.mayBuzzAt,
                mayRelaxAt = outcome.data.mayRelaxAt,
            ),
        )
    }

    private fun handleError(error: Throwable?) {
        when (error) {
            is CommonException.NeedLoginException -> {
                postSideEffect(LocationDetailSideEffect.ShowErrorToast(error.snackBarMessage))
                postSideEffect(LocationDetailSideEffect.GoToLoginFragment)
            }

            else -> postSideEffect(
                LocationDetailSideEffect.ShowErrorToast(
                    error?.message ?: CommonException.UnknownException().snackBarMessage,
                ),
            )
        }
    }

    fun popBackStack() {
        postSideEffect(LocationDetailSideEffect.PopBackStack)
    }

    override fun reduceState(
        current: LocationDetailViewState,
        event: LocationDetailEvent,
    ): LocationDetailViewState = when (event) {
        is LocationDetailEvent.UpdateInitData -> current.copy(
            isInitializing = event.isInitializing,
            congestion = event.congestion,
            locationName = event.name,
            mayRelaxAt = event.mayRelaxAt,
            mayBuzzAt = event.mayBuzzAt,
        )
    }
}
