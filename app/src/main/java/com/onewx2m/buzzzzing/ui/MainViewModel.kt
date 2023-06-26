package com.onewx2m.buzzzzing.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.onewx2m.domain.Outcome
import com.onewx2m.domain.usecase.ReissueJwtUseCase
import com.onewx2m.mvi.Event
import com.onewx2m.mvi.MviViewModel
import com.onewx2m.mvi.SideEffect
import com.onewx2m.mvi.ViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject


@HiltViewModel
class MainViewModel @Inject constructor(
    private val reissueJwtUseCase: ReissueJwtUseCase
) : MviViewModel<MainViewState, MainEvent, MainSideEffect>(MainViewState()) {

    var preDrawRemoveFlag = false

    fun reissueJwtAndNavigateFragmentAndHideSplashScreen() = viewModelScope.launch {
        reissueJwtUseCase().collect { outcome ->
            when(outcome) {
                is Outcome.Loading -> {  }
                is Outcome.Success -> { postSideEffect(MainSideEffect.GoToHomeFragment) }
                is Outcome.Failure -> {  }
            }
        }
        postEvent(MainEvent.HideSplashScreen)
    }

    fun isDestinationInBottomNavigationBarInitialFragment(
        destinationId: Int,
        bottomNavigationBarInitialFragmentIds: List<Int>
    ) {
        if (destinationId in bottomNavigationBarInitialFragmentIds) postEvent(MainEvent.ShowBottomNavigationBar)
        else postEvent(MainEvent.HideBottomNavigationBar)
    }

    override fun reduceState(current: MainViewState, event: MainEvent): MainViewState =
        when (event) {
            is MainEvent.HideBottomNavigationBar -> current.copy(isBottomNavigationBarVisible = false)
            is MainEvent.ShowBottomNavigationBar -> current.copy(isBottomNavigationBarVisible = true)
            is MainEvent.HideSplashScreen -> current.copy(isSplashScreenVisible = false)
        }
}