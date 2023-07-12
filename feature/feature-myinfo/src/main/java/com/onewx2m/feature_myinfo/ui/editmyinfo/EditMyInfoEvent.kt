package com.onewx2m.feature_myinfo.ui.editmyinfo

import android.net.Uri
import com.onewx2m.design_system.components.button.MainButtonState
import com.onewx2m.mvi.Event

sealed interface EditMyInfoEvent : Event {
    object ChangeNicknameLayoutStateLengthError : EditMyInfoEvent
    object ChangeNicknameLayoutStateCharError : EditMyInfoEvent
    object ChangeNicknameLayoutStateNormal : EditMyInfoEvent
    object ChangeNicknameLayoutStateInactive : EditMyInfoEvent
    object ChangeNicknameLayoutStateLoading : EditMyInfoEvent
    object ChangeNicknameLayoutStateSuccess : EditMyInfoEvent
    object ChangeNicknameLayoutStateOverlap : EditMyInfoEvent
    object ChangeNicknameLayoutStateUnavailable : EditMyInfoEvent
    data class UpdateProfileUri(val uri: Uri) : EditMyInfoEvent

    object ChangeEmailLayoutStateNormal : EditMyInfoEvent
    object ChangeEmailLayoutStateInactive : EditMyInfoEvent
    object ChangeEmailLayoutStateSuccess : EditMyInfoEvent
    object ChangeEmailLayoutStateUnavailable : EditMyInfoEvent

    data class ChangeMainButtonState(val state: MainButtonState) : EditMyInfoEvent
}
