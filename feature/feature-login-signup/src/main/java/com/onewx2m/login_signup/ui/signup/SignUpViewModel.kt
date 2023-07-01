package com.onewx2m.login_signup.ui.signup

import com.onewx2m.design_system.components.button.MainButtonState
import com.onewx2m.mvi.MviViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor() :
    MviViewModel<SignUpViewState, SignUpEvent, SignUpSideEffect>(SignUpViewState()) {
    override fun reduceState(current: SignUpViewState, event: SignUpEvent): SignUpViewState = when (event) {
        is SignUpEvent.ChangeMainButtonState -> current.copy(mainButtonState = event.mainButtonState)
    }

    fun postChangeMainButtonStateEvent(mainButtonState: MainButtonState) {
        postEvent(SignUpEvent.ChangeMainButtonState(mainButtonState))
    }
}
