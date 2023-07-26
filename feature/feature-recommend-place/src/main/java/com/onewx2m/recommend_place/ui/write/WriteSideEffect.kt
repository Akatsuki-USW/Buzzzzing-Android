package com.onewx2m.recommend_place.ui.write

import com.onewx2m.mvi.SideEffect

sealed interface WriteSideEffect : SideEffect {
    data class MoreScroll(val scrollY: Int) : WriteSideEffect
}
