package com.onewx2m.feature_myinfo.ui.myinfo

import com.onewx2m.mvi.MviViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MyInfoViewModel @Inject constructor() : MviViewModel<MyInfoViewState, MyInfoEvent, MyInfoSideEffect>(MyInfoViewState()) {
    override fun reduceState(current: MyInfoViewState, event: MyInfoEvent): MyInfoViewState {
        TODO("Not yet implemented")
    }

    fun goToEdit() {
        postSideEffect(MyInfoSideEffect.GoToEdit)
    }
}