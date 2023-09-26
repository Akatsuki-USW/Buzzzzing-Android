package com.onewx2m.login_signup.ui.login

import com.onewx2m.design_system.components.button.SnsLoginButtonType
import com.onewx2m.mvi.ViewState

data class LoginViewState(
    val kakaoLoginButtonState: SnsLoginButtonType = SnsLoginButtonType.ENABLE
) : ViewState