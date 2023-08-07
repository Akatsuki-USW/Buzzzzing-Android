package com.onewx2m.feature_myinfo.ui.myarticle.spotwritten

import com.onewx2m.design_system.components.recyclerview.spot.SpotItem
import com.onewx2m.mvi.Event

sealed interface SpotWrittenEvent : Event {
    data class UpdateSpotList(val spotList: List<SpotItem>) :
        SpotWrittenEvent

    object Initializing : SpotWrittenEvent
    object Initialized : SpotWrittenEvent
}
