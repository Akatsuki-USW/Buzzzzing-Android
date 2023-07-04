package com.onewx2m.login_signup.ui.signup.profileandnickname

import com.onewx2m.mvi.SideEffect

sealed interface ProfileAndNicknameSideEffect : SideEffect {
    data class MoreScroll(val scrollY: Int) : ProfileAndNicknameSideEffect
    data class ShowErrorToast(val message: String) : ProfileAndNicknameSideEffect
}
