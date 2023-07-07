package com.onewx2m.remote.api

import com.onewx2m.remote.ApiResult
import com.onewx2m.remote.model.ApiResponse
import com.onewx2m.remote.model.response.UserInfoResponse
import retrofit2.http.GET

interface UserApi {
    companion object {
        const val USER = "users"
    }

    @GET("$USER/me")
    suspend fun getMyInfo(): ApiResult<ApiResponse<UserInfoResponse>>
}
