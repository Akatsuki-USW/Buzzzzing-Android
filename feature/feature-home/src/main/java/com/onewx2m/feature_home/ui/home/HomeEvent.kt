package com.onewx2m.feature_home.ui.home

import com.onewx2m.design_system.components.recyclerview.buzzzzingmedium.BuzzzzingMediumItem
import com.onewx2m.mvi.Event

sealed interface HomeEvent : Event {
    data class UpdateBuzzzzingMediumItem(val buzzzzingMediumItem: List<BuzzzzingMediumItem>) : HomeEvent
}
