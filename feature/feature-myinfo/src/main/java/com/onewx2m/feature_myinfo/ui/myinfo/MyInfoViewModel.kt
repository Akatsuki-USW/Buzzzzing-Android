package com.onewx2m.feature_myinfo.ui.myinfo

import com.onewx2m.mvi.MviViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MyInfoViewModel @Inject constructor() :
    MviViewModel<MyInfoViewState, MyInfoEvent, MyInfoSideEffect>(MyInfoViewState.Default) {
    override fun reduceState(current: MyInfoViewState, event: MyInfoEvent): MyInfoViewState {
        return current
    }

    fun goToEdit() {
        postSideEffect(MyInfoSideEffect.GoToEdit)
    }

    fun goToMyArticle() {
        postSideEffect(MyInfoSideEffect.GoToMyArticle)
    }

    fun goToNotification() {
        postSideEffect(MyInfoSideEffect.GoToNotification)
    }

    fun showOpenLicenses() {
        postSideEffect(MyInfoSideEffect.ShowOpenLicenses)
    }
}
