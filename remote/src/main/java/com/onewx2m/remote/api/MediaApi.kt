package com.onewx2m.remote.api

import com.onewx2m.remote.ApiResult
import com.onewx2m.remote.model.ApiResponse
import com.onewx2m.remote.model.response.UploadFileListResponse
import okhttp3.MultipartBody
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface MediaApi {
    companion object {
        const val FILE = "files"
    }

    @Multipart
    @POST("$FILE/change")
    suspend fun changeImage(
        @Part type: MultipartBody.Part,
        @Part toDeleteUrls: MultipartBody.Part,
        @Part files: List<MultipartBody.Part>,
    ): ApiResult<ApiResponse<UploadFileListResponse>>
}
