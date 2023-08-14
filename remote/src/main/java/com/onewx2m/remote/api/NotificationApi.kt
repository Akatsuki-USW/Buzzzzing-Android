package com.onewx2m.remote.api

import com.onewx2m.remote.ApiResult
import com.onewx2m.remote.api.BuzzzzingLocationApi.Companion.ME
import com.onewx2m.remote.api.UserApi.Companion.USER
import com.onewx2m.remote.model.ApiResponse
import com.onewx2m.remote.model.response.NotificationListResponse
import retrofit2.http.POST
import retrofit2.http.Path

interface NotificationApi {
    companion object {
        const val NOTIFICATION = "notification"
        const val NOTIFICATION_ID = "notificationId"
    }

    @POST("$NOTIFICATION/{$NOTIFICATION_ID}/read")
    suspend fun readNotification(
        @Path(NOTIFICATION_ID) notificationId: Int,
    ): ApiResult<ApiResponse<Unit>>

    @POST("$NOTIFICATION/$USER/$ME")
    suspend fun getNotificationList(): ApiResult<ApiResponse<NotificationListResponse>>
}
