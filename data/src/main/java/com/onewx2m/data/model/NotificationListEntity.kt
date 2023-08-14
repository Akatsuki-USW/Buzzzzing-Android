package com.onewx2m.data.model

import com.onewx2m.domain.model.Notification
import com.onewx2m.domain.model.NotificationList

data class NotificationListEntity(
    val notifications: List<NotificationEntity>,
)

data class NotificationEntity(
    val notificationId: Int,
    val notificationType: String,
    val targetEntity: String,
    val redirectTargetId: Int,
    val title: String,
    val body: String,
    val createdAt: String,
    val isRead: Boolean,
)

internal fun NotificationListEntity.toDomain() = NotificationList(
    notifications = notifications.map { it.toDomain() }
)

internal fun NotificationEntity.toDomain() = Notification(
    notificationId = notificationId,
    notificationType = notificationType,
    targetEntity = targetEntity,
    redirectTargetId = redirectTargetId,
    title = title,
    body = body,
    createdAt = createdAt,
    isRead = isRead,
)
