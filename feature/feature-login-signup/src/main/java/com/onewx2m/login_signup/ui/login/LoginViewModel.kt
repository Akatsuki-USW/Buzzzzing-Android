package com.onewx2m.login_signup.ui.login

import com.onewx2m.design_system.components.snsLoginButton.SnsLoginButtonState
import com.onewx2m.mvi.MviViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor() : MviViewModel<LoginViewState, LoginEvent, LoginSideEffect>(LoginViewState()) {

    fun onClickKakaoLoginButton() {
        postEvent(LoginEvent.KakaoLoginButtonStateToLoading)
        postSideEffect(LoginSideEffect.TryLoginByKakao)
    }

    override fun reduceState(current: LoginViewState, event: LoginEvent): LoginViewState
    = when(event) {
        LoginEvent.KakaoLoginButtonStateToEnable -> current.copy(kakaoLoginButtonState = SnsLoginButtonState.Enable)
        LoginEvent.KakaoLoginButtonStateToLoading -> current.copy(kakaoLoginButtonState = SnsLoginButtonState.Loading)
    }
}