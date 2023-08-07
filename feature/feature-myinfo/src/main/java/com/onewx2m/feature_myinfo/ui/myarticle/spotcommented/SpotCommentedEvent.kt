package com.onewx2m.feature_myinfo.ui.myarticle.spotcommented

import com.onewx2m.design_system.components.recyclerview.spot.SpotItem
import com.onewx2m.mvi.Event

sealed interface SpotCommentedEvent : Event {
    data class UpdateSpotList(val spotList: List<SpotItem>) :
        SpotCommentedEvent

    object Initializing : SpotCommentedEvent
    object Initialized : SpotCommentedEvent
}
