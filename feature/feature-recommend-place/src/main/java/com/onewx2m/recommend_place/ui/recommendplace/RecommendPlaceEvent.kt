package com.onewx2m.recommend_place.ui.recommendplace

import com.onewx2m.design_system.components.recyclerview.spot.SpotItem
import com.onewx2m.design_system.components.recyclerview.spotcategoryselector.SpotCategoryItem
import com.onewx2m.mvi.Event

sealed interface RecommendPlaceEvent : Event {
    data class UpdateSpotList(val spotList: List<SpotItem>) : RecommendPlaceEvent
    data class UpdateSpotCategoryItems(
        val spotCategoryItems: List<SpotCategoryItem>,
    ) : RecommendPlaceEvent

    data class UpdateSelectedSpotCategoryItem(
        val selectedSpotCategoryItem: SpotCategoryItem?,
    ) : RecommendPlaceEvent
}
