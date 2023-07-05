package com.onewx2m.login_signup.ui.signup.email

import com.onewx2m.design_system.components.button.MainButtonState
import com.onewx2m.mvi.MviViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class EmailViewModel @Inject constructor() :
    MviViewModel<EmailViewState, EmailEvent, EmailSideEffect>(
        EmailViewState(),
    ) {

    override fun reduceState(
        current: EmailViewState,
        event: EmailEvent,
    ): EmailViewState = when (event) {
        EmailEvent.ChangeEmailLayoutStateInactive -> TODO()
        EmailEvent.ChangeEmailLayoutStateNormal -> TODO()
        EmailEvent.ChangeEmailLayoutStateSuccess -> TODO()
        EmailEvent.ChangeEmailLayoutStateUnavailable -> TODO()
    }

    suspend fun checkEmailRegexAndUpdateUi(Email: CharSequence?, isFocused: Boolean): Boolean {
        if (Email.isNullOrEmpty()) {
            postEmailStateNormalOrInactiveEvent(isFocused)
            return false
        }

//            if (Regex.Email_LENGTH.toRegex().matches(Email).not()) {
//                postEvent(EmailEvent.ChangeEmailLayoutStateUnavailable)
//                return false
//            }

        postEvent(EmailEvent.ChangeEmailLayoutStateNormal)
        return true
    }

    private fun postEmailStateNormalOrInactiveEvent(isFocused: Boolean) {
        if (isFocused) {
            postEvent(EmailEvent.ChangeEmailLayoutStateNormal)
        } else {
            postEvent(EmailEvent.ChangeEmailLayoutStateInactive)
        }
    }

    fun doWhenKeyboardShow(currentScrollY: Int, additionalScroll: Int) {
        if (currentScrollY < additionalScroll) {
            postSideEffect(
                EmailSideEffect.MoreScroll(
                    additionalScroll - currentScrollY,
                ),
            )
        }
    }

    fun postSignUpButtonStateDisableSideEffect() {
        postSideEffect(EmailSideEffect.ChangeSignUpButtonState(MainButtonState.DISABLE))
    }
}
