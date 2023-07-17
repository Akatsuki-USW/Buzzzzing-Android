package com.onewx2m.feature_home.ui.home

import com.onewx2m.mvi.SideEffect

sealed interface HomeSideEffect : SideEffect {
    object ShowLocationBottomSheet : HomeSideEffect
    object ShowCongestionBottomSheet : HomeSideEffect
}
