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

    data class ShowReplyLayout(val nickname: String) : SpotDetailEvent
    object HideReplyLayout : SpotDetailEvent

    object Initialized : SpotDetailEvent

    object Refresh : SpotDetailEvent

    data class UpdateComment(val content: String, val needRender: Boolean = false) : SpotDetailEvent
}
