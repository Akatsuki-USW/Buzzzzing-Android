package com.onewx2m.login_signup.ui.login

import androidx.lifecycle.viewModelScope
import com.onewx2m.design_system.components.snsLoginButton.SnsLoginButtonState
import com.onewx2m.domain.Outcome
import com.onewx2m.domain.exception.NeedSignUpException
import com.onewx2m.domain.usecase.LoginByKakaoUseCase
import com.onewx2m.mvi.MviViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginByKakaoUseCase: LoginByKakaoUseCase,
) : MviViewModel<LoginViewState, LoginEvent, LoginSideEffect>(LoginViewState()) {

    fun onClickKakaoLoginButton() {
        postEvent(LoginEvent.KakaoLoginButtonStateToLoading)
        postSideEffect(LoginSideEffect.TryLoginByKakao)
    }

    fun handleKakaoLoginSuccess(kakaoAccessToken: String) = viewModelScope.launch {
        loginByKakaoUseCase(kakaoAccessToken).collect {
            when (it) {
                is Outcome.Loading -> {}
                is Outcome.Success -> { postSideEffect(LoginSideEffect.GoToHomeFragment) }
                is Outcome.Failure -> { handleLoginByKakaoUsecaseFail(it.error) }
            }
        }
    }

    private fun handleLoginByKakaoUsecaseFail(error: Throwable?) = when (error) {
        is NeedSignUpException -> { Timber.tag("kakao").d("회원가입 필요 ${error.signToken}") }
        else -> { }
    }

    fun handleKakaoLoginFail() {
        postEvent(LoginEvent.KakaoLoginButtonStateToEnable)
    }

    override fun reduceState(current: LoginViewState, event: LoginEvent): LoginViewState =
        when (event) {
            LoginEvent.KakaoLoginButtonStateToEnable -> current.copy(kakaoLoginButtonState = SnsLoginButtonState.Enable)
            LoginEvent.KakaoLoginButtonStateToLoading -> current.copy(kakaoLoginButtonState = SnsLoginButtonState.Loading)
        }
}
