package com.onewx2m.buzzzzing.ui

import com.onewx2m.mvi.SideEffect

sealed interface MainSideEffect : SideEffect {
    object GoToHomeFragment : MainSideEffect
    data class ShowErrorToast(val msg: String) : MainSideEffect
    object FinishActivity : MainSideEffect
    object ShowBackPressedMsg : MainSideEffect
    data class ChangeBackPressedCallbackEnable(val isEnable: Boolean) : MainSideEffect
}
