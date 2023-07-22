package com.onewx2m.feature_home.ui.locationdetail

import com.onewx2m.design_system.enum.Congestion
import com.onewx2m.mvi.Event

sealed interface LocationDetailEvent : Event {
    data class UpdateInitData(val name: String, val congestion: Congestion, val mayBuzzAt: Int?, val mayRelaxAt: Int?, val isInitializing: Boolean = false) : LocationDetailEvent
}
