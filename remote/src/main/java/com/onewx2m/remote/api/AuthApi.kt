package com.onewx2m.remote.api

import com.onewx2m.remote.ApiResult
import com.onewx2m.remote.model.request.LoginRequest
import com.onewx2m.remote.model.response.JwtResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthApi {
    @POST("/auth/login")
    suspend fun login(
        @Body request: LoginRequest
    )

    @POST("/auth/reissue")
    suspend fun reIssueBuzzzzingJwt(

    ): ApiResult<JwtResponse>
}