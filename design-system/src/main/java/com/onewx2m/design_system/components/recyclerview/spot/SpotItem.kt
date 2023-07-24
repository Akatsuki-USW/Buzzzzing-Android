package com.onewx2m.design_system.components.recyclerview.spot

import com.onewx2m.design_system.enum.ItemViewType

data class SpotItem(
    val type: ItemViewType = ItemViewType.NORMAL,
    val id: Int = -1,
    val title: String = "",
    val address: String = "",
    val thumbnailImageUrl: String? = null,
    val isBookmarked: Boolean = false,
    val userNickname: String = "",
    val userProfileImageUrl: String = "",
)
