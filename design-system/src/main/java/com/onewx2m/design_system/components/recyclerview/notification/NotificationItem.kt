package com.onewx2m.design_system.components.recyclerview.notification

data class NotificationItem(
    val notificationId: Int = -1,
    val notificationType: String = "",
    val targetEntity: String = "",
    val redirectTargetId: Int = -1,
    val title: String = "",
    val body: String = "",
    val createdAt: String = "",
    val isRead: Boolean = false,
)
