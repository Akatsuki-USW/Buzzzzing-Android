package com.onewx2m.feature_home.ui.locationdetail.spot

import com.onewx2m.design_system.components.recyclerview.spot.SpotItem
import com.onewx2m.design_system.components.recyclerview.spotcategoryselector.SpotCategoryItem
import com.onewx2m.mvi.Event

sealed interface SpotEvent : Event {
    data class UpdateSpotList(val spotList: List<SpotItem>) : SpotEvent
    data class UpdateSpotCategoryItems(
        val spotCategoryItems: List<SpotCategoryItem>,
    ) : SpotEvent

    data class UpdateSelectedSpotCategoryItem(
        val selectedSpotCategoryItem: SpotCategoryItem?,
    ) : SpotEvent
}
