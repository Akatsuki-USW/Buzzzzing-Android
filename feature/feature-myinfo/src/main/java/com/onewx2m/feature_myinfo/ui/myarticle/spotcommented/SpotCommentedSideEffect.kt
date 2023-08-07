package com.onewx2m.feature_myinfo.ui.myarticle.spotcommented

import com.onewx2m.mvi.SideEffect

sealed interface SpotCommentedSideEffect : SideEffect {
    data class ShowErrorToast(val msg: String) : SpotCommentedSideEffect
    object GoToLoginFragment : SpotCommentedSideEffect
    data class GoToSpotDetailFragment(val spotId: Int) : SpotCommentedSideEffect
}
