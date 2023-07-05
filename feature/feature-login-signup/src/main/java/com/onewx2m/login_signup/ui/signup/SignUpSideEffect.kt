package com.onewx2m.login_signup.ui.signup

import com.onewx2m.mvi.SideEffect

sealed interface SignUpSideEffect : SideEffect {
    object GoToPrevPage : SignUpSideEffect
    object HideKeyboard : SignUpSideEffect
    object PlayLottie : SignUpSideEffect
    object StopLottie : SignUpSideEffect
    data class ShowErrorToast(val msg: String) : SignUpSideEffect
}
