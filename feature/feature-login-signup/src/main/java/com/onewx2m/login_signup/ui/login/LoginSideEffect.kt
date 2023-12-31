package com.onewx2m.login_signup.ui.login

import com.onewx2m.mvi.SideEffect

sealed interface LoginSideEffect : SideEffect {
    object TryLoginByKakao : LoginSideEffect
    object GoToHomeFragment : LoginSideEffect
    data class GoToSignUpFragment(val signToken: String) : LoginSideEffect
    data class ShowErrorToast(val message: String) : LoginSideEffect
}
