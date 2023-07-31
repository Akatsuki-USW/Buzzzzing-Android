package com.onewx2m.recommend_place.ui.spotdetail

import com.onewx2m.mvi.Event
import com.onewx2m.recommend_place.ui.spotdetail.adapter.SpotDetailContentItem

sealed interface SpotDetailEvent : Event {
    data class UpdateSpotDetailContent(val content: SpotDetailContentItem) : SpotDetailEvent

    object Initialized : SpotDetailEvent
}
