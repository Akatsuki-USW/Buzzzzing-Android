package com.onewx2m.login_signup.ui.signup.email

import com.onewx2m.core_ui.util.Regex
import com.onewx2m.design_system.components.button.MainButtonType
import com.onewx2m.design_system.components.textinputlayout.TextInputLayoutState
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
        EmailEvent.ChangeEmailLayoutStateInactive -> current.copy(
            emailLayoutState = TextInputLayoutState.INACTIVE,
            emailLayoutHelperTextResId = com.onewx2m.design_system.R.string.word_space,
        )

        EmailEvent.ChangeEmailLayoutStateNormal -> current.copy(
            emailLayoutState = TextInputLayoutState.FOCUSED,
            emailLayoutHelperTextResId = com.onewx2m.design_system.R.string.word_space,
        )

        EmailEvent.ChangeEmailLayoutStateSuccess -> current.copy(
            emailLayoutState = TextInputLayoutState.SUCCESS,
            emailLayoutHelperTextResId = com.onewx2m.core_ui.R.string.text_input_layout_email_available_email,
        )

        EmailEvent.ChangeEmailLayoutStateUnavailable -> current.copy(
            emailLayoutState = TextInputLayoutState.ERROR,
            emailLayoutHelperTextResId = com.onewx2m.core_ui.R.string.text_input_layout_email_unavailable_email,
        )
    }

    fun checkEmailRegexAndUpdateUi(email: CharSequence?, isFocused: Boolean) {
        if (email.isNullOrEmpty()) {
            postEmailStateNormalOrInactiveEvent(isFocused)
            return
        }

        if (Regex.EMAIL.toRegex().matches(email).not()) {
            postEvent(EmailEvent.ChangeEmailLayoutStateUnavailable)
            return
        }

        postEvent(EmailEvent.ChangeEmailLayoutStateSuccess)
        postSideEffect(EmailSideEffect.UpdateSignUpEmail(email.toString()))
        postSideEffect(EmailSideEffect.ChangeSignUpButtonState(MainButtonType.POSITIVE))
        return
    }

    fun postEmailStateNormalOrInactiveEvent(isFocused: Boolean) {
        if (isFocused) {
            postEvent(EmailEvent.ChangeEmailLayoutStateNormal)
        } else {
            postEvent(EmailEvent.ChangeEmailLayoutStateInactive)
        }
    }

    fun postSignUpButtonStateDisableSideEffect() {
        postSideEffect(EmailSideEffect.ChangeSignUpButtonState(MainButtonType.DISABLE))
    }
}
