package com.onewx2m.login_signup.ui.signup

import android.net.Uri
import com.onewx2m.design_system.components.button.MainButtonState
import com.onewx2m.feature_login_signup.R
import com.onewx2m.login_signup.ui.signup.adapter.SignUpFragmentType
import com.onewx2m.mvi.MviViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor() :
    MviViewModel<SignUpViewState, SignUpEvent, SignUpSideEffect>(SignUpViewState()) {

    private val viewPagerFirstPosition = 0
    private val viewPagerLastPosition = SignUpFragmentType.values().size - 1

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
        postSideEffect(SignUpSideEffect.HideViewPagerAndShowSignUpLottie)
        postSideEffect(SignUpSideEffect.HideKeyboard)
        postChangeMainButtonStateEvent(MainButtonState.LOADING)
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
