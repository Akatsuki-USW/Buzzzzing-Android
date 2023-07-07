package com.onewx2m.remote.api

import com.onewx2m.remote.ApiResult
import com.onewx2m.remote.api.AuthApi.Companion.AUTH
import com.onewx2m.remote.api.UserApi.Companion.USER
import com.onewx2m.remote.model.ApiResponse
import com.onewx2m.remote.model.request.SignUpRequest
import com.onewx2m.remote.model.response.JwtResponse
import com.onewx2m.remote.model.response.UploadFileListResponse
import com.onewx2m.remote.model.response.VerifyNicknameResponse
import okhttp3.MultipartBody
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path

interface OtherApi {
    companion object {
        const val FILE = "files"
    }

    @GET("$USER/check/nickname/{nickname}")
    suspend fun verifyNickname(
        @Path("nickname") request: String,
    ): ApiResult<ApiResponse<VerifyNicknameResponse>>

    @Multipart
    @POST(FILE)
    suspend fun uploadImage(
        @Part type: MultipartBody.Part,
        @Part files: List<MultipartBody.Part>,
    ): ApiResult<ApiResponse<UploadFileListResponse>>

    @POST("$AUTH/signup")
    suspend fun signUp(
        @Body request: SignUpRequest,
    ): ApiResult<ApiResponse<JwtResponse>>
}
