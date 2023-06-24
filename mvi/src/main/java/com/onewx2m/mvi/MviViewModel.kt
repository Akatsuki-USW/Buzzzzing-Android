package com.onewx2m.mvi

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.runningFold
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

abstract class MviViewModel<VIEW_STATE: ViewState, EVENT: Event, SIDE_EFFECT: SideEffect>(
    initialState: VIEW_STATE
) : ViewModel() {

    private val events = Channel<EVENT>()

    val state = events.receiveAsFlow()
        .runningFold(initialState, ::reduceState)
        .stateIn(viewModelScope, SharingStarted.Eagerly, initialState)

    private val _sideEffects = Channel<SIDE_EFFECT>()
    val sideEffects = _sideEffects.receiveAsFlow()

    /**
     * event에 따른 state를 변경해주는 함수입니다.
     *
     * ```kotlin
     * return when(event) {
     *       MainEvent.Loading -> {
     *          current.copy(loading = true)
     *       }
     *       is MainEvent.Loaded -> {
     *           current.copy(loading = false, users = event.users)
     *       }
     * }
     * ```
     * @param current 현재 뷰의 상태를 나타냅니다.
     * @param event 수신한 event입니다.
     */
    abstract fun reduceState(current: VIEW_STATE, event: EVENT): VIEW_STATE

    protected fun postEvent(event: EVENT) = viewModelScope.launch {
        events.send(event)
    }

    protected fun postSideEffect(sideEffect: SIDE_EFFECT) = viewModelScope.launch {
        _sideEffects.send(sideEffect)
    }
}