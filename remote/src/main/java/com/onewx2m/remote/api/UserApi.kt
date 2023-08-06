package com.onewx2m.remote.api

import com.onewx2m.remote.ApiResult
import com.onewx2m.remote.model.ApiResponse
import com.onewx2m.remote.model.request.BlockUserRequest
import com.onewx2m.remote.model.request.ReportRequest
import com.onewx2m.remote.model.request.UserInfoRequest
import com.onewx2m.remote.model.response.UserInfoResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

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
}
