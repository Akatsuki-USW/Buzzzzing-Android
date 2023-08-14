package com.onewx2m.feature_myinfo.ui.notification

import com.onewx2m.design_system.components.recyclerview.notification.NotificationItem
import com.onewx2m.mvi.Event

sealed interface NotificationEvent : Event {
    data class UpdateSpotList(val notificationList: List<NotificationItem>) :
        NotificationEvent
}
