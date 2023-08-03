package com.onewx2m.recommend_place.ui.spotdetail

import com.onewx2m.design_system.components.recyclerview.spotcomment.parent.SpotParentCommentItem
import com.onewx2m.mvi.Event
import com.onewx2m.recommend_place.ui.spotdetail.adapter.SpotDetailContentItem

sealed interface SpotDetailEvent : Event {
    data class UpdateSpotDetailContent(val content: SpotDetailContentItem) : SpotDetailEvent
    data class UpdateSpotParentCommentList(val commentList: List<SpotParentCommentItem>) :
        SpotDetailEvent

    object ShowSmallLoadingLottie : SpotDetailEvent
    object HideSmallLoadingLottie : SpotDetailEvent

    object Initialized : SpotDetailEvent
}
