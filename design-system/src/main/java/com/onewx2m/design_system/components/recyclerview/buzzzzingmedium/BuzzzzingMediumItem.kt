package com.onewx2m.design_system.components.recyclerview.buzzzzingmedium

import com.onewx2m.design_system.enum.ItemViewType

data class BuzzzzingMediumItem(
    val type: ItemViewType = ItemViewType.NORMAL,
    val id: Int = -1,
    val isBookmarked: Boolean = false,
    val locationName: String = "",
    val congestionSymbol: String = "",
    val iconUrl: String = "",
)
