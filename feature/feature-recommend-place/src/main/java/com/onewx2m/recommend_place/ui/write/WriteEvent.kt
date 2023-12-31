package com.onewx2m.recommend_place.ui.write

import com.onewx2m.design_system.components.recyclerview.picture.PictureItem
import com.onewx2m.design_system.components.recyclerview.spotcategoryselector.SpotCategoryItem
import com.onewx2m.mvi.Event

sealed interface WriteEvent : Event {
    data class InitViewState(
        val state: WriteViewState,
    ) : WriteEvent

    data class ChangeKakaoLocationInputLayout(
        val kakaoLocation: String,
    ) : WriteEvent

    data class ChangeBuzzzzingLocationInputLayout(
        val buzzzzingLocation: String,
    ) : WriteEvent

    data class UpdateTitle(
        val title: String,
        val needRender: Boolean,
    ) : WriteEvent

    data class UpdateContent(
        val content: String,
        val needRender: Boolean,
    ) : WriteEvent

    data class UpdateSpotCategoryItems(
        val spotCategoryItems: List<SpotCategoryItem>,
    ) : WriteEvent

    data class UpdateSelectedSpotCategoryItem(
        val selectedSpotCategoryItem: SpotCategoryItem?,
    ) : WriteEvent

    data class UpdatePictures(
        val pictures: List<PictureItem>,
    ) : WriteEvent

    object LoadingState : WriteEvent
    object NormalState : WriteEvent

    object SuccessState : WriteEvent
}
