package com.onewx2m.feature_home.ui.locationdetail

import com.onewx2m.design_system.enum.Congestion
import com.onewx2m.mvi.ViewState

data class LocationDetailViewState(
    val isInitializingDetailInfo: Boolean = true,
    val isInitializingViewPagerData: Boolean = true,
    val congestion: Congestion = Congestion.NORMAL,
    val locationName: String = "",
    val mayBuzzAt: Int? = null,
    val mayRelaxAt: Int? = null,
) : ViewState
