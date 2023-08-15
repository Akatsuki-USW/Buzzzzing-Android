package com.onewx2m.feature_myinfo.ui.ban

import com.onewx2m.mvi.SideEffect

sealed interface BanSideEffect : SideEffect {
    data class ShowErrorToast(val msg: String) : BanSideEffect
    object GoToLoginFragment : BanSideEffect

    object PopBackStack : BanSideEffect
}
