package com.onewx2m.data.datasource

import com.onewx2m.data.model.NotificationListEntity
import com.onewx2m.domain.Outcome
import kotlinx.coroutines.flow.Flow

interface RemoteNotificationDataSource {
    suspend fun getNotificationList(): Flow<Outcome<NotificationListEntity>>
    suspend fun readNotification(notificationId: Int): Flow<Outcome<Unit>>
}
