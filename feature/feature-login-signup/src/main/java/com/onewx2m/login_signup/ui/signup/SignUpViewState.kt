package com.onewx2m.login_signup.ui.signup

import com.onewx2m.design_system.components.button.MainButtonState
import com.onewx2m.mvi.ViewState

data class SignUpViewState(
    val mainButtonState: MainButtonState = MainButtonState.DISABLE
) : ViewState