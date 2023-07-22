package com.onewx2m.feature_home.ui.locationdetail

import com.onewx2m.mvi.SideEffect

sealed interface LocationDetailSideEffect : SideEffect {
    data class ShowErrorToast(val msg: String) : LocationDetailSideEffect
    object GoToLoginFragment : LocationDetailSideEffect
    object PopBackStack : LocationDetailSideEffect
    data class InitViewPagerAndTabLayout(val congestion: String) : LocationDetailSideEffect
}