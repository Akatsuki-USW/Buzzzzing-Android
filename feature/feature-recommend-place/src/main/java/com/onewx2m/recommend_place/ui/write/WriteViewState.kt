package com.onewx2m.recommend_place.ui.write

import com.onewx2m.design_system.components.button.MainButtonState
import com.onewx2m.design_system.components.recyclerview.picture.PictureItem
import com.onewx2m.design_system.components.recyclerview.spotcategoryselector.SpotCategoryItem
import com.onewx2m.mvi.ViewState

data class WriteViewState(
    val title: String = "",
    val needTitleRender: Boolean = false,
    val kakaoLocation: String = "",
    val buzzzzingLocation: String = "",
    val content: String = "",
    val needContentRender: Boolean = false,
    val spotCategoryItems: List<SpotCategoryItem> = emptyList(),
    val selectedSpotCategoryItem: SpotCategoryItem? = null,
    val pictures: List<PictureItem> = emptyList(),
    val isScrollbarVisible: Boolean = true,
    val isLoadingLottieVisible: Boolean = false,
    val isSuccessLottieVisible: Boolean = false,
) : ViewState {
    val mainButtonState: MainButtonState =
        if (
            isLoadingLottieVisible
        ) {
            MainButtonState.LOADING
        } else if (title.isNotEmpty() &&
            kakaoLocation.isNotEmpty() &&
            buzzzzingLocation.isNotEmpty() &&
            content.isNotEmpty() &&
            selectedSpotCategoryItem != null ||
            isSuccessLottieVisible
        ) {
            MainButtonState.POSITIVE
        } else {
            MainButtonState.DISABLE
        }

    val pictureUrls
        get() = pictures.filter { it.url.isNotEmpty() }.map { it.url }

    val pictureUris
        get() = pictures.filter { it.uri != null }.map { it.uri!! }
}
