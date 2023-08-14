package com.onewx2m.domain.repository

import com.onewx2m.domain.Outcome
import com.onewx2m.domain.model.Notification
import com.onewx2m.domain.model.NotificationList
import kotlinx.coroutines.flow.Flow

interface NotificationRepository {
    suspend fun getNotificationList(): Flow<Outcome<NotificationList>>
    suspend fun readNotification(notificationId: Int): Flow<Outcome<Unit>>
}
