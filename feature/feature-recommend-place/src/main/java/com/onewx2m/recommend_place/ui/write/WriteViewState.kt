package com.onewx2m.recommend_place.ui.write

import com.onewx2m.design_system.components.button.MainButtonState
import com.onewx2m.design_system.components.recyclerview.spotcategoryselector.SpotCategoryItem
import com.onewx2m.mvi.ViewState

data class WriteViewState(
    val title: String = "",
    val kakaoLocation: String = "",
    val buzzzzingLocation: String = "",
    val content: String = "",
    val spotCategoryItems: List<SpotCategoryItem> = emptyList(),
    val selectedSpotCategoryItem: SpotCategoryItem? = null,
) : ViewState {
    val mainButtonState: MainButtonState =
        if (title.isNotEmpty() && kakaoLocation.isNotEmpty() && buzzzzingLocation.isNotEmpty() && content.isNotEmpty() && selectedSpotCategoryItem != null) MainButtonState.POSITIVE else MainButtonState.DISABLE
}
