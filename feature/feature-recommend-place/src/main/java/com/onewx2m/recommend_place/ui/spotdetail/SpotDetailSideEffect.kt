package com.onewx2m.recommend_place.ui.spotdetail

import com.onewx2m.mvi.SideEffect

sealed interface SpotDetailSideEffect : SideEffect {
    data class ShowErrorToast(val msg: String) : SpotDetailSideEffect
    object GoToLoginFragment : SpotDetailSideEffect
    object GoToWriteFragment : SpotDetailSideEffect
}
