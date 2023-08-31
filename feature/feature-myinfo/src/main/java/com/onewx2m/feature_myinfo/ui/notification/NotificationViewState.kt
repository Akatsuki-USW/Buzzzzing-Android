package com.onewx2m.feature_myinfo.ui.notification

import com.onewx2m.design_system.components.recyclerview.notification.NotificationItem
import com.onewx2m.design_system.enum.ItemViewType
import com.onewx2m.domain.model.NotificationList
import com.onewx2m.mvi.ViewState

data class NotificationViewState(
    val notificationList: List<NotificationItem> = emptyList(),
) : ViewState

internal fun NotificationList.toItem() = notifications.map {
    NotificationItem(
        notificationId = it.notificationId,
        notificationType = it.notificationType,
        targetEntity = it.targetEntity,
        redirectTargetId = it.redirectTargetId,
        title = it.title,
        body = it.body,
        createdAt = it.createdAt,
        isRead = it.isRead,
    )
}
