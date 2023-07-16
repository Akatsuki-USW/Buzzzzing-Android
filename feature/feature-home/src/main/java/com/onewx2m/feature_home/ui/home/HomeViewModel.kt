package com.onewx2m.feature_home.ui.home

import androidx.lifecycle.viewModelScope
import com.onewx2m.design_system.components.recyclerview.buzzzzingmedium.BuzzzzingMediumItem
import com.onewx2m.domain.collectOutcome
import com.onewx2m.domain.usecase.GetBuzzzzingLocationUseCase
import com.onewx2m.mvi.MviViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getBuzzzzingLocationUseCase: GetBuzzzzingLocationUseCase,
) : MviViewModel<HomeViewState, HomeEvent, HomeSideEffect>(
    HomeViewState(),
) {
    override fun reduceState(current: HomeViewState, event: HomeEvent): HomeViewState =
        when (event) {
            is HomeEvent.UpdateBuzzzzingMediumItem -> current.copy(buzzzzingMediumItem = event.buzzzzingMediumItem)
        }

    fun getBuzzzzingLocation() = viewModelScope.launch {
        getBuzzzzingLocationUseCase(0, null, null, "BUZZING", null).collectOutcome(
            handleSuccess = {
                val item = it.data.content.map { data ->
                    BuzzzzingMediumItem(
                        id = data.id,
                        isBookmarked = data.isBookmarked,
                        locationName = data.name,
                        congestionSymbol = data.congestionSymbol,
                        iconUrl = data.categoryIconUrl,
                    )
                }

                postEvent(HomeEvent.UpdateBuzzzzingMediumItem(item))
            },
        )
    }
}
