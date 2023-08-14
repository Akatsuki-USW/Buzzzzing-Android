package com.onewx2m.remote.model.response

import com.onewx2m.data.model.NotificationEntity
import com.onewx2m.data.model.NotificationListEntity
import kotlinx.serialization.Serializable

@Serializable
data class NotificationListResponse(
    val notifications: List<NotificationResponse>,
)

@Serializable
data class NotificationResponse(
    val notificationId: Int,
    val notificationType: String,
    val targetEntity: String,
    val redirectTargetId: Int,
    val title: String,
    val body: String,
    val createdAt: String,
    val isRead: Boolean,
)

internal fun NotificationListResponse.toEntity() = NotificationListEntity(
    notifications = notifications.map { it.toEntity() },
)

internal fun NotificationResponse.toEntity() = NotificationEntity(
    notificationId = notificationId,
    notificationType = notificationType,
    targetEntity = targetEntity,
    redirectTargetId = redirectTargetId,
    title = title,
    body = body,
    createdAt = createdAt,
    isRead = isRead,
)
