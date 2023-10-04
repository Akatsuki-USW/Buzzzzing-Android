package com.onewx2m.login_signup.ui.signup

import com.onewx2m.design_system.components.button.MainButtonState
import com.onewx2m.mvi.ViewState

data class SignUpViewState(
    val mainButtonState: MainButtonState = MainButtonState.DISABLE,
    val pagerPosition: Int = 0,
    val buttonTextRes: Int = com.onewx2m.design_system.R.string.word_next,
    val isPagerVisible: Boolean = true,
    val isLottieVisible: Boolean = false
) : ViewState
