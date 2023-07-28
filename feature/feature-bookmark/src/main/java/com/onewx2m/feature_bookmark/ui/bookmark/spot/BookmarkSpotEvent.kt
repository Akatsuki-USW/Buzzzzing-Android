package com.onewx2m.feature_bookmark.ui.bookmark.spot

import com.onewx2m.design_system.components.recyclerview.buzzzzingmedium.BuzzzzingMediumItem
import com.onewx2m.design_system.components.recyclerview.spot.SpotItem
import com.onewx2m.feature_bookmark.ui.bookmark.buzzzzing.BookmarkBuzzzzingEvent
import com.onewx2m.mvi.Event

sealed interface BookmarkSpotEvent : Event {
    data class UpdateSpotList(val spotList: List<SpotItem>) :
        BookmarkSpotEvent
    object Initializing : BookmarkSpotEvent
    object Initialized : BookmarkSpotEvent
}
