package com.onewx2m.remote.api

import com.onewx2m.remote.ApiResult
import com.onewx2m.remote.model.ApiResponse
import com.onewx2m.remote.model.response.JwtResponse
import com.onewx2m.remote.model.response.VerifyNicknameResponse
import retrofit2.http.POST
import retrofit2.http.Path

interface OtherApi {
    companion object {
        private const val USER = "users"
    }

    @POST("$USER/check/nickname/{nickname}")
    suspend fun verifyNickname(
        @Path("nickname") request: String,
    ): ApiResult<ApiResponse<VerifyNicknameResponse>>
}
