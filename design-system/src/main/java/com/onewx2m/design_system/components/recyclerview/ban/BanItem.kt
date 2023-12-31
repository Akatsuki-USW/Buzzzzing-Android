package com.onewx2m.design_system.components.recyclerview.ban

import com.onewx2m.design_system.enum.ItemViewType

data class BanItem(
    val id: Int = -1,
    val title: String = "",
    val body: String = "",
    val banStartAt: String = "",
    val banEndedAt: String = "",
    val type: ItemViewType = ItemViewType.NORMAL,
)
