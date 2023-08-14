package com.onewx2m.remote.datasource

import com.onewx2m.data.datasource.RemoteNotificationDataSource
import com.onewx2m.data.model.NotificationListEntity
import com.onewx2m.domain.Outcome
import com.onewx2m.remote.api.NotificationApi
import com.onewx2m.remote.model.response.toEntity
import com.onewx2m.remote.onFailure
import com.onewx2m.remote.onSuccess
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class RemoteNotificationDataSourceImpl @Inject constructor(
    private val api: NotificationApi,
) : RemoteNotificationDataSource {

    override suspend fun getNotificationList(): Flow<Outcome<NotificationListEntity>> = flow {
        api.getNotificationList().onSuccess { body ->
            emit(Outcome.Success(body.data!!.toEntity()))
        }.onFailure {
            emit(Outcome.Failure(it))
        }
    }

    override suspend fun readNotification(notificationId: Int): Flow<Outcome<Unit>> = flow {
        api.readNotification(notificationId).onSuccess {
            emit(Outcome.Success(Unit))
        }.onFailure {
            emit(Outcome.Failure(it))
        }
    }
}
