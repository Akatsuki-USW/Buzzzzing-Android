package com.onewx2m.feature_myinfo.ui.myarticle.spotwritten

import com.onewx2m.mvi.SideEffect

sealed interface SpotWrittenSideEffect : SideEffect {
    data class ShowErrorToast(val msg: String) : SpotWrittenSideEffect
    object GoToLoginFragment : SpotWrittenSideEffect
    data class GoToSpotDetailFragment(val spotId: Int) : SpotWrittenSideEffect
}
