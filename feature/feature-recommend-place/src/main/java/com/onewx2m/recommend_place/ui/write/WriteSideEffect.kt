package com.onewx2m.recommend_place.ui.write

import com.onewx2m.core_ui.model.WriteContent
import com.onewx2m.mvi.SideEffect

sealed interface WriteSideEffect : SideEffect {
    data class MoreScroll(val scrollY: Int) : WriteSideEffect
    object ShowBuzzzzingLocationBottomSheet : WriteSideEffect
    object ShowKakaoLocationBottomSheet : WriteSideEffect
    object GetPermissionAndShowImagePicker : WriteSideEffect
    object PlayLoadingLottie : WriteSideEffect
    object StopLoadingLottie : WriteSideEffect
    object StopSuccessLottie : WriteSideEffect
    object PlaySuccessLottie : WriteSideEffect
    object HideKeyboard : WriteSideEffect
    data class ShowErrorToast(val msg: String) : WriteSideEffect
    object GoToRecommendPlace : WriteSideEffect
    data class PopBackStack(val writeContent: WriteContent) : WriteSideEffect
}
