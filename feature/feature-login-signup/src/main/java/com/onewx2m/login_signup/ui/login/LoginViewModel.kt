package com.onewx2m.login_signup.ui.login

import androidx.lifecycle.viewModelScope
import com.onewx2m.core_ui.util.BuzzzzingUser
import com.onewx2m.design_system.components.button.SnsLoginButtonState
import com.onewx2m.domain.Outcome
import com.onewx2m.domain.collectOutcome
import com.onewx2m.domain.exception.NeedSignUpException
import com.onewx2m.domain.exception.RevokeUntilMonthUserException
import com.onewx2m.domain.exception.common.CommonException
import com.onewx2m.domain.model.UserInfo
import com.onewx2m.domain.usecase.GetMyInfoUseCase
import com.onewx2m.domain.usecase.LoginByKakaoUseCase
import com.onewx2m.mvi.MviViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginByKakaoUseCase: LoginByKakaoUseCase,
    private val getMyInfoUseCase: GetMyInfoUseCase,
) : MviViewModel<LoginViewState, LoginEvent, LoginSideEffect>(LoginViewState()) {

    fun onClickKakaoLoginButton() {
        postEvent(LoginEvent.KakaoLoginButtonStateToLoading)
        postSideEffect(LoginSideEffect.TryLoginByKakao)
    }

    fun handleKakaoLoginSuccess(kakaoAccessToken: String) = viewModelScope.launch {
        loginByKakaoUseCase(kakaoAccessToken)
            .collectOutcome(
                handleSuccess = { getMyInfoAndSave() },
                handleFail = { handleError(it.error, ::handleLoginByKakaoUsecaseFail) },
            )
    }

    private fun getMyInfoAndSave() = viewModelScope.launch {
        getMyInfoUseCase().collectOutcome(
            handleSuccess = ::handleGetMyInfoSuccess,
            handleFail = ::handleGetMyInfoFail,
        )
    }

    private fun handleGetMyInfoSuccess(outcome: Outcome.Success<UserInfo>) {
        BuzzzzingUser.setInfo(
            email = outcome.data.email,
            nickname = outcome.data.nickname,
            profileImageUrl = outcome.data.profileImageUrl,
        )
        postSideEffect(LoginSideEffect.GoToHomeFragment)
    }

    private fun handleGetMyInfoFail(outcome: Outcome.Failure<UserInfo>) {
        postSideEffect(
            LoginSideEffect.ShowErrorToast(
                outcome.error?.message ?: CommonException.UnknownException().snackBarMessage,
            ),
        )
    }

    private fun handleError(
        error: Throwable?,
        handleNotCommonException: (error: Throwable?) -> Unit = {},
    ) {
        when (error) {
            is CommonException -> handleCommonError(error)
            else -> handleNotCommonException(error)
        }

        postEvent(LoginEvent.KakaoLoginButtonStateToEnable)
    }

    private fun handleCommonError(error: CommonException) {
        val errorToastMessage = when (error) {
            CommonException.NeedLoginException() -> CommonException.UnknownException().snackBarMessage
            else -> error.snackBarMessage
        }

        postSideEffect(LoginSideEffect.ShowErrorToast(errorToastMessage))
    }

    private fun handleLoginByKakaoUsecaseFail(error: Throwable?) {
        when (error) {
            is NeedSignUpException -> postSideEffect(LoginSideEffect.GoToSignUpFragment(error.signToken))
            is RevokeUntilMonthUserException -> postSideEffect(LoginSideEffect.ShowErrorToast(error.message))
            else -> postSideEffect(LoginSideEffect.ShowErrorToast(CommonException.UnknownException().snackBarMessage))
        }
    }

    fun handleKakaoLoginFail() {
        postEvent(LoginEvent.KakaoLoginButtonStateToEnable)
    }

    override fun reduceState(current: LoginViewState, event: LoginEvent): LoginViewState =
        when (event) {
            LoginEvent.KakaoLoginButtonStateToEnable -> current.copy(kakaoLoginButtonState = SnsLoginButtonState.ENABLE)
            LoginEvent.KakaoLoginButtonStateToLoading -> current.copy(kakaoLoginButtonState = SnsLoginButtonState.LOADING)
        }
}
