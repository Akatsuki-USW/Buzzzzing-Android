package com.onewx2m.login_signup.ui.signup

import com.onewx2m.design_system.components.button.MainButtonState
import com.onewx2m.feature_login_signup.R
import com.onewx2m.mvi.ViewState

data class SignUpViewState(
    val mainButtonState: MainButtonState = MainButtonState.DISABLE,
    val viewPagerPosition: Int = 0,
    val buttonTextRes: Int = com.onewx2m.design_system.R.string.word_next,
    val isViewPagerVisible: Boolean = true,
    val isLottieVisible: Boolean = false
) : ViewState
