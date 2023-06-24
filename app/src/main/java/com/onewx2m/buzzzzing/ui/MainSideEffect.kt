package com.onewx2m.buzzzzing.ui

import com.onewx2m.mvi.SideEffect

sealed interface MainSideEffect : SideEffect {
    object GoToHomeFragment : MainSideEffect
}