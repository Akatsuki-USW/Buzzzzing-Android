package com.onewx2m.login_signup.ui.signup

import com.onewx2m.mvi.SideEffect

sealed interface SignUpSideEffect : SideEffect {
    object GoToPrevPage : SignUpSideEffect
}