package com.onewx2m.feature_home.ui.locationdetail.spot

import com.onewx2m.design_system.components.recyclerview.spot.SpotItem
import com.onewx2m.design_system.components.recyclerview.spotcategoryselector.SpotCategoryItem
import com.onewx2m.mvi.ViewState

data class SpotViewState(
    val spotList: List<SpotItem> = emptyList(),
    val spotCategoryItems: List<SpotCategoryItem> = emptyList(),
    val selectedSpotCategoryItem: SpotCategoryItem? = null,
) : ViewState
