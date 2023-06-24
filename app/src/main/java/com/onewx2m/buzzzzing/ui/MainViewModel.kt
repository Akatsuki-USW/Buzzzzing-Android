package com.onewx2m.buzzzzing.ui

import androidx.lifecycle.ViewModel
import com.onewx2m.domain.usecase.ReissueJwtUseCase
import com.onewx2m.mvi.Event
import com.onewx2m.mvi.MviViewModel
import com.onewx2m.mvi.SideEffect
import com.onewx2m.mvi.ViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class MainViewModel @Inject constructor(
    private val reissueJwtUseCase: ReissueJwtUseCase
): MviViewModel<MainViewState, MainEvent, SideEffect.Default>(MainViewState()) {

    fun isDestinationInBottomNavigationBarInitialFragment(destinationId: Int, bottomNavigationBarInitialFragmentIds: List<Int>) {
        if(destinationId in bottomNavigationBarInitialFragmentIds) postEvent(MainEvent.ShowBottomNavigationBar)
        else postEvent(MainEvent.HideBottomNavigationBar)
    }

    override fun reduceState(current: MainViewState, event: MainEvent): MainViewState
    = when(event) {
        is MainEvent.HideBottomNavigationBar -> current.copy(isBottomNavigationBarVisible = false)
        is MainEvent.ShowBottomNavigationBar -> current.copy(isBottomNavigationBarVisible = true)
    }
}