package com.onewx2m.login_signup.ui.signup

import android.net.Uri
import androidx.lifecycle.viewModelScope
import com.onewx2m.core_ui.util.BuzzzzingUser
import com.onewx2m.core_ui.util.ImageUtil
import com.onewx2m.design_system.components.button.MainButtonState
import com.onewx2m.domain.Outcome
import com.onewx2m.domain.exception.common.CommonException
import com.onewx2m.domain.usecase.GetMyInfoUseCase
import com.onewx2m.domain.usecase.SignUpUseCase
import com.onewx2m.feature_login_signup.R
import com.onewx2m.login_signup.ui.signup.adapter.SignUpFragmentType
import com.onewx2m.mvi.MviViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val signUpUsecase: SignUpUseCase,
    private val imageUtil: ImageUtil,
    private val getMyInfoUseCase: GetMyInfoUseCase,
) :
    MviViewModel<SignUpViewState, SignUpEvent, SignUpSideEffect>(SignUpViewState()) {

    private val viewPagerFirstPosition = 0
    private val viewPagerLastPosition = SignUpFragmentType.values().size - 1

    var signToken: String = ""
    var availableNickname: String = ""
    var profileUri: Uri? = null
    var email: String = ""

    private var isSigningUp = false

    override fun reduceState(current: SignUpViewState, event: SignUpEvent): SignUpViewState =
        when (event) {
            is SignUpEvent.ChangeMainButtonState -> current.copy(mainButtonState = event.mainButtonState)
            is SignUpEvent.ChangeViewPagerPosition -> {
                if (event.position in viewPagerFirstPosition..viewPagerLastPosition) {
                    current.copy(
                        viewPagerPosition = event.position,
                        buttonTextRes = if (event.position == viewPagerLastPosition) R.string.sign_up_button_finish else com.onewx2m.design_system.R.string.word_next,
                    )
                } else {
                    current
                }
            }

            SignUpEvent.HideViewPagerAndShowLottie -> current.copy(
                isViewPagerVisible = false,
                isLottieVisible = true,
            )

            SignUpEvent.ShowViewPagerAndHideLottie -> current.copy(
                isViewPagerVisible = true,
                isLottieVisible = false,
            )
        }

    fun postChangeMainButtonStateEvent(mainButtonState: MainButtonState) {
        if (isSigningUp && mainButtonState != MainButtonState.LOADING) return
        postEvent(SignUpEvent.ChangeMainButtonState(mainButtonState))
    }

    fun onClickMainButton() {
        val currentPosition = state.value.viewPagerPosition
        if (currentPosition == viewPagerLastPosition) {
            trySignUp()
        } else {
            goToNextPage(currentPosition)
        }
    }

    private fun trySignUp() {
        isSigningUp = true
        postEvent(SignUpEvent.HideViewPagerAndShowLottie)
        postSideEffect(SignUpSideEffect.PlayLottie)
        postSideEffect(SignUpSideEffect.HideKeyboard)
        postChangeMainButtonStateEvent(MainButtonState.LOADING)
        signUp()
    }

    private fun signUp() = viewModelScope.launch {
        val file = profileUri?.let {
            val nullableFile = imageUtil.uriToOptimizeImageFile(it)
            if (nullableFile == null) {
                handleSignUpFail()
                return@let null
            }
            nullableFile
        }

        signUpUsecase(
            file = file,
            signToken = signToken,
            nickname = availableNickname,
            email = email,
        ).collect { outcome ->
            when (outcome) {
                Outcome.Loading -> {}
                is Outcome.Success -> getMyInfoAndSave()
                is Outcome.Failure -> handleSignUpFail(outcome.error)
            }
        }
    }

    private fun getMyInfoAndSave() = viewModelScope.launch {
        getMyInfoUseCase().collect {
            when (it) {
                is Outcome.Loading -> {}
                is Outcome.Success -> {
                    BuzzzzingUser.setInfo(
                        email = it.data.email,
                        nickname = it.data.nickname,
                        profileImageUrl = it.data.profileImageUrl,
                    )
                    postSideEffect(SignUpSideEffect.GoToHomeFragment)
                }

                is Outcome.Failure -> {
                    handleSignUpFail(it.error)
                }
            }
        }
    }

    private fun handleSignUpFail(error: Throwable? = null) {
        postEvent(SignUpEvent.ChangeMainButtonState(MainButtonState.POSITIVE))
        postEvent(SignUpEvent.ShowViewPagerAndHideLottie)
        postSideEffect(
            SignUpSideEffect.ShowErrorToast(
                error?.message
                    ?: CommonException.UnknownException().snackBarMessage,
            ),
        )
    }

    private fun goToNextPage(currentPosition: Int) {
        postChangeMainButtonStateEvent(MainButtonState.DISABLE)
        postEvent(SignUpEvent.ChangeViewPagerPosition(currentPosition + 1))
    }

    fun onBackPressed() {
        if (isSigningUp) return
        val currentPosition = state.value.viewPagerPosition
        if (currentPosition == viewPagerFirstPosition) {
            postSideEffect(SignUpSideEffect.GoToPrevPage)
        } else {
            postEvent(SignUpEvent.ChangeViewPagerPosition(currentPosition - 1))
        }
    }
}
