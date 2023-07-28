package com.onewx2m.feature_bookmark.ui.bookmark.buzzzzing

import com.onewx2m.design_system.components.recyclerview.buzzzzingmedium.BuzzzzingMediumItem
import com.onewx2m.mvi.Event

sealed interface BookmarkBuzzzzingEvent : Event {
    data class UpdateBuzzzzingMediumItem(val buzzzzingMediumItem: List<BuzzzzingMediumItem>) :
        BookmarkBuzzzzingEvent
    object Initializing : BookmarkBuzzzzingEvent
    object Initialized : BookmarkBuzzzzingEvent
}
