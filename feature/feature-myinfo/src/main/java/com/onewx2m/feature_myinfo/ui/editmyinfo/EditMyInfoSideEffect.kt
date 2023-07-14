package com.onewx2m.feature_myinfo.ui.editmyinfo

import com.onewx2m.mvi.SideEffect

sealed interface EditMyInfoSideEffect : SideEffect {
    data class MoreScroll(val scrollY: Int) : EditMyInfoSideEffect
    data class ShowErrorToast(val message: String) : EditMyInfoSideEffect
    object GetPermissionAndShowImagePicker : EditMyInfoSideEffect
    object GoToPrev : EditMyInfoSideEffect
    object PlayLottie : EditMyInfoSideEffect
    object StopLottie : EditMyInfoSideEffect
    object HideKeyboard : EditMyInfoSideEffect
}
