package com.onewx2m.feature_myinfo.ui.ban

import com.onewx2m.design_system.components.recyclerview.ban.BanItem
import com.onewx2m.mvi.Event

sealed interface BanEvent : Event {
    data class UpdateBanList(val banList: List<BanItem>) :
        BanEvent
}
