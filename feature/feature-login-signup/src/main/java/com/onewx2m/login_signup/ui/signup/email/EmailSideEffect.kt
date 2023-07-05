package com.onewx2m.login_signup.ui.signup.email // ktlint-disable filename

import com.onewx2m.design_system.components.button.MainButtonState
import com.onewx2m.mvi.SideEffect

sealed interface EmailSideEffect : SideEffect {
    data class ChangeSignUpButtonState(val buttonState: MainButtonState) : EmailSideEffect
    data class UpdateSignUpEmail(val email: String) : EmailSideEffect
}
