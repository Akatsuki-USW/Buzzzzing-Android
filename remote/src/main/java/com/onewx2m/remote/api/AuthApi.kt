package com.onewx2m.remote.api

import com.onewx2m.remote.ApiResult
import com.onewx2m.remote.model.ApiResponse
import com.onewx2m.remote.model.request.JwtReIssueRequest
import com.onewx2m.remote.model.request.LoginRequest
import com.onewx2m.remote.model.response.JwtResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthApi {
    companion object {
        private const val AUTH = "api/auth"
    }

    @POST("$AUTH/login")
    suspend fun login(
        @Body request: LoginRequest
    ): ApiResult<ApiResponse<JwtResponse>>

    @POST("$AUTH/reissue")
    suspend fun reIssueBuzzzzingJwt(
        @Body request: JwtReIssueRequest
    ): ApiResult<ApiResponse<JwtResponse>>
}