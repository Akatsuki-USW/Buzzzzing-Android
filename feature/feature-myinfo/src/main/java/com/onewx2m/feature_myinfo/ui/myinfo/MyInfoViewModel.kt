package com.onewx2m.feature_myinfo.ui.myinfo

import androidx.lifecycle.viewModelScope
import com.onewx2m.domain.collectOutcome
import com.onewx2m.domain.exception.common.CommonException
import com.onewx2m.domain.usecase.LogoutUseCase
import com.onewx2m.domain.usecase.RevokeUseCase
import com.onewx2m.mvi.MviViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MyInfoViewModel @Inject constructor(
    private val logoutUseCase: LogoutUseCase,
    private val revokeUseCase: RevokeUseCase,
) :
    MviViewModel<MyInfoViewState, MyInfoEvent, MyInfoSideEffect>(MyInfoViewState()) {
    override fun reduceState(current: MyInfoViewState, event: MyInfoEvent): MyInfoViewState =
        when (event) {
            MyInfoEvent.HideSmallLoadingLottie -> current.copy(isSmallLottieVisible = false)
            MyInfoEvent.ShowSmallLoadingLottie -> current.copy(isSmallLottieVisible = true)
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

    fun goToBanList() {
        postSideEffect(MyInfoSideEffect.GoToBanList)
    }

    fun showLogoutDialog() {
        postSideEffect(MyInfoSideEffect.Logout)
    }

    fun showQuitDialog() {
        postSideEffect(MyInfoSideEffect.Quit)
    }

    fun logout() = viewModelScope.launch {
        logoutUseCase().onStart {
            postEvent(MyInfoEvent.ShowSmallLoadingLottie)
        }.onCompletion {
            postEvent(MyInfoEvent.HideSmallLoadingLottie)
        }.collectOutcome(
            handleSuccess = { postSideEffect(MyInfoSideEffect.GoToLoginFragment) },
            handleFail = { handleError(it.error) },
        )
    }

    fun revoke() = viewModelScope.launch {
        revokeUseCase().onStart {
            postEvent(MyInfoEvent.ShowSmallLoadingLottie)
        }.onCompletion {
            postEvent(MyInfoEvent.HideSmallLoadingLottie)
        }.collectOutcome(
            handleSuccess = { postSideEffect(MyInfoSideEffect.GoToLoginFragment) },
            handleFail = { handleError(it.error) },
        )
    }

    private fun handleError(error: Throwable?) {
        when (error) {
            is CommonException.NeedLoginException -> {
                postSideEffect(MyInfoSideEffect.ShowErrorToast(error.snackBarMessage))
                postSideEffect(MyInfoSideEffect.GoToLoginFragment)
            }

            else -> postSideEffect(
                MyInfoSideEffect.ShowErrorToast(
                    error?.message ?: CommonException.UnknownException().snackBarMessage,
                ),
            )
        }
    }
}
