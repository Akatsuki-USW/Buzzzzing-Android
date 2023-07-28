package com.onewx2m.feature_bookmark.ui.bookmark.spot

import com.onewx2m.design_system.components.recyclerview.buzzzzingmedium.BuzzzzingMediumItem
import com.onewx2m.design_system.components.recyclerview.spot.SpotItem
import com.onewx2m.mvi.ViewState

data class BookmarkSpotViewState(
    val spotList: List<SpotItem> = emptyList(),
    val isInitializing: Boolean = true,
) : ViewState
