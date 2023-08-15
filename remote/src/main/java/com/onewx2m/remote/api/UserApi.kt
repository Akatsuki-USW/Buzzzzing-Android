package com.onewx2m.remote.api

import com.onewx2m.data.model.SpotListEntity
import com.onewx2m.domain.Outcome
import com.onewx2m.remote.ApiResult
import com.onewx2m.remote.api.BuzzzzingLocationApi.Companion.CURSOR_ID
import com.onewx2m.remote.api.BuzzzzingLocationApi.Companion.LOCATION
import com.onewx2m.remote.api.BuzzzzingLocationApi.Companion.ME
import com.onewx2m.remote.api.SpotApi.Companion.SPOT
import com.onewx2m.remote.model.ApiResponse
import com.onewx2m.remote.model.request.BlockUserRequest
import com.onewx2m.remote.model.request.ReportRequest
import com.onewx2m.remote.model.request.UserInfoRequest
import com.onewx2m.remote.model.response.BanListResponse
import com.onewx2m.remote.model.response.SpotListResponse
import com.onewx2m.remote.model.response.UserInfoResponse
import kotlinx.coroutines.flow.Flow
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface UserApi {
    companion object {
        const val USER = "users"
    }

    @POST("/reports")
    suspend fun report(@Body request: ReportRequest): ApiResult<ApiResponse<Unit>>

    @POST("$USER/hide")
    suspend fun blockUser(@Body request: BlockUserRequest): ApiResult<ApiResponse<Unit>>

    @GET("$USER/me")
    suspend fun getMyInfo(): ApiResult<ApiResponse<UserInfoResponse>>

    @POST("$USER/me/profile")
    suspend fun editMyInfo(@Body userInfoRequest: UserInfoRequest): ApiResult<ApiResponse<UserInfoResponse>>

    @GET("$LOCATION/$SPOT/$ME")
    suspend fun getSpotWritten(
        @Query(CURSOR_ID) cursorId: Int,
    ): ApiResult<ApiResponse<SpotListResponse>>

    @GET("$LOCATION/$SPOT/$ME/commented")
    suspend fun getSpotCommented(
        @Query(CURSOR_ID) cursorId: Int,
    ): ApiResult<ApiResponse<SpotListResponse>>

    @POST("$USER/revoke")
    suspend fun revoke(): ApiResult<ApiResponse<Unit>>

    @GET("/bans/me")
    suspend fun getBanReasonList(): ApiResult<ApiResponse<BanListResponse>>
}
