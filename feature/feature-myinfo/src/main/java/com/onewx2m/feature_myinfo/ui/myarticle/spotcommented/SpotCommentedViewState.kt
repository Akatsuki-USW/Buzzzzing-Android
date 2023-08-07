package com.onewx2m.feature_myinfo.ui.myarticle.spotcommented

import com.onewx2m.design_system.components.recyclerview.spot.SpotItem
import com.onewx2m.mvi.ViewState

data class SpotCommentedViewState(
    val spotList: List<SpotItem> = emptyList(),
    val isInitializing: Boolean = true,
) : ViewState
