package com.onewx2m.design_system.components.recyclerview.buzzzzingshort

import com.onewx2m.design_system.enum.ItemViewType

data class BuzzzzingShortItem(
    val viewType: ItemViewType = ItemViewType.NORMAL,
    val id: Int = -1,
    val name: String = "",
    val categoryIconUrl: String = "",
)
