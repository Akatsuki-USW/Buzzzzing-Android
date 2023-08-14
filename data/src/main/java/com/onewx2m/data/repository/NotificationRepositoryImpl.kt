package com.onewx2m.data.repository

import com.onewx2m.data.datasource.RemoteNotificationDataSource
import com.onewx2m.data.extension.flatMapOutcomeSuccess
import com.onewx2m.data.model.toDomain
import com.onewx2m.domain.Outcome
import com.onewx2m.domain.model.NotificationList
import com.onewx2m.domain.repository.NotificationRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class NotificationRepositoryImpl @Inject constructor(
    private val remoteNotificationDataSource: RemoteNotificationDataSource,
) : NotificationRepository {
    override suspend fun getNotificationList(): Flow<Outcome<NotificationList>> {
        return remoteNotificationDataSource.getNotificationList().flatMapOutcomeSuccess {
            it.toDomain()
        }
    }

    override suspend fun readNotification(notificationId: Int): Flow<Outcome<Unit>> {
        return remoteNotificationDataSource.readNotification(notificationId)
    }
}
