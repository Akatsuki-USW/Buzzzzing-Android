package com.onewx2m.domain.model

data class NotificationList(
    val notifications: List<Notification>,
)

data class Notification(
    val notificationId: Int,
    val notificationType: String,
    val targetEntity: String,
    val redirectTargetId: Int,
    val title: String,
    val body: String,
    val createdAt: String,
    val isRead: Boolean,
)
