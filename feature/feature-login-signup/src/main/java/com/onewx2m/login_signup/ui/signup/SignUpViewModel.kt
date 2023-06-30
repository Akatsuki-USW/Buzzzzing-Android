package com.onewx2m.login_signup.ui.signup

import com.onewx2m.mvi.MviViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor() :
    MviViewModel<SignUpViewState, SignUpEvent, SignUpSideEffect>(SignUpViewState()) {
    override fun reduceState(current: SignUpViewState, event: SignUpEvent): SignUpViewState {
        TODO("Not yet implemented")
    }
}
