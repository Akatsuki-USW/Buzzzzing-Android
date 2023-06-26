package com.onewx2m.login_signup.ui.login

import com.onewx2m.mvi.SideEffect

sealed interface LoginSideEffect : SideEffect {
    object TryLoginByKakao : LoginSideEffect
    object GoToHomeFragment : LoginSideEffect
    object GoToSignUpFragment : LoginSideEffect
}
