package com.onewx2m.login_signup.ui.signup.profileandnickname

import android.net.Uri
import com.onewx2m.mvi.Event

sealed interface ProfileAndNicknameEvent : Event {
    object ChangeNicknameLayoutStateLengthError : ProfileAndNicknameEvent
    object ChangeNicknameLayoutStateCharError : ProfileAndNicknameEvent
    object ChangeNicknameLayoutStateNormal : ProfileAndNicknameEvent
    object ChangeNicknameLayoutStateInactive : ProfileAndNicknameEvent
    object ChangeNicknameLayoutStateLoading : ProfileAndNicknameEvent
    object ChangeNicknameLayoutStateSuccess : ProfileAndNicknameEvent
    object ChangeNicknameLayoutStateOverlap : ProfileAndNicknameEvent
    object ChangeNicknameLayoutStateUnavailable : ProfileAndNicknameEvent
    data class UpdateProfileUri(val uri: Uri) : ProfileAndNicknameEvent
    data class UpdateNickname(val nickname: String) : ProfileAndNicknameEvent
}
