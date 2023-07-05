package com.onewx2m.login_signup.ui.signup

import android.net.Uri
import com.onewx2m.design_system.components.button.MainButtonState
import com.onewx2m.login_signup.ui.signup.adapter.SignUpFragmentType
import com.onewx2m.mvi.MviViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor() :
    MviViewModel<SignUpViewState, SignUpEvent, SignUpSideEffect>(SignUpViewState()) {

    private val viewPagerFirstPosition = 0
    private val viewPagerLastPosition = SignUpFragmentType.values().size

    var availableNickname: String = ""
    var profileUri: Uri? = null
    var email: String = ""

    override fun reduceState(current: SignUpViewState, event: SignUpEvent): SignUpViewState =
        when (event) {
            is SignUpEvent.ChangeMainButtonState -> current.copy(mainButtonState = event.mainButtonState)
            is SignUpEvent.ChangeViewPagerPosition -> {
                if (event.position in viewPagerFirstPosition until viewPagerLastPosition) {
                    current.copy(viewPagerPosition = event.position)
                } else {
                    current
                }
            }
        }

    fun postChangeMainButtonStateEvent(mainButtonState: MainButtonState) {
        postEvent(SignUpEvent.ChangeMainButtonState(mainButtonState))
    }

    fun onClickMainButton() {
        val currentPosition = state.value.viewPagerPosition
        postChangeMainButtonStateEvent(MainButtonState.DISABLE)
        postEvent(SignUpEvent.ChangeViewPagerPosition(currentPosition + 1))
    }

    fun onBackPressed() {
        val currentPosition = state.value.viewPagerPosition
        if (currentPosition == viewPagerFirstPosition || currentPosition == viewPagerLastPosition) {
            postSideEffect(SignUpSideEffect.GoToPrevPage)
        } else {
            postEvent(SignUpEvent.ChangeViewPagerPosition(currentPosition - 1))
        }
    }
}
