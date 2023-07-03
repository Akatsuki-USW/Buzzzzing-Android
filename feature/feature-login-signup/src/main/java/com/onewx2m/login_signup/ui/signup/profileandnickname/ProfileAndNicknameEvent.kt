package com.onewx2m.login_signup.ui.signup.profileandnickname

import com.onewx2m.mvi.Event

sealed interface ProfileAndNicknameEvent : Event {
    object ChangeNicknameLayoutStateLengthError : ProfileAndNicknameEvent
    object ChangeNicknameLayoutStateCharError : ProfileAndNicknameEvent
    object ChangeNicknameLayoutStateNormal : ProfileAndNicknameEvent
    object ChangeNicknameLayoutStateInactive : ProfileAndNicknameEvent
    object ChangeNicknameLayoutStateLoading : ProfileAndNicknameEvent
    object ChangeNicknameLayoutStateSuccess : ProfileAndNicknameEvent
}
