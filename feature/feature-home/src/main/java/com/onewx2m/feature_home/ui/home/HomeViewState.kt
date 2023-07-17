package com.onewx2m.feature_home.ui.home

import com.onewx2m.design_system.components.recyclerview.buzzzzingmedium.BuzzzzingMediumItem
import com.onewx2m.mvi.ViewState

data class HomeViewState(
    val buzzzzingMediumItem: List<BuzzzzingMediumItem> = emptyList(),
    val congestionFilter: String = "",
    val locationFilter: String = "",
    val keyword: String? = "",
) : ViewState
