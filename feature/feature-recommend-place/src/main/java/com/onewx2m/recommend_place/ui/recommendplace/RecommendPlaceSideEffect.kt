package com.onewx2m.recommend_place.ui.recommendplace

import com.onewx2m.mvi.SideEffect

sealed interface RecommendPlaceSideEffect : SideEffect {
    data class ShowErrorToast(val msg: String) : RecommendPlaceSideEffect
    object GoToLoginFragment : RecommendPlaceSideEffect
}
