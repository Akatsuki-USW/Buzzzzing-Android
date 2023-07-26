package com.onewx2m.design_system.components.recyclerview.kakaolocation

import com.onewx2m.design_system.enum.ItemViewType

data class KakaoLocationItem(
    val viewType: ItemViewType = ItemViewType.NORMAL,
    val placeName: String = "",
    val addressName: String = "",
    val roadAddressName: String = "",
)
