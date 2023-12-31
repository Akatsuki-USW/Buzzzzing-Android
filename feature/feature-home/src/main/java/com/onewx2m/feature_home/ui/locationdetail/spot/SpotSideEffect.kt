package com.onewx2m.feature_home.ui.locationdetail.spot

import com.onewx2m.feature_home.ui.locationdetail.LocationDetailSideEffect
import com.onewx2m.mvi.SideEffect

sealed interface SpotSideEffect : SideEffect {
    data class ShowErrorToast(val msg: String) : SpotSideEffect
    object GoToLoginFragment : SpotSideEffect
    data class GoToSpotDetailFragment(val spotId: Int) : SpotSideEffect
}
