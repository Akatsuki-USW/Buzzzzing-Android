package com.onewx2m.remote.api

import com.onewx2m.remote.ApiResult
import com.onewx2m.remote.BuildConfig
import com.onewx2m.remote.model.response.KakaoLocationInfoResponse
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface KakaoLocationApi {
    @GET("/v2/local/search/keyword")
    suspend fun getLocationInfo(
        @Query("query") query: String,
        @Query("page") page: Int,
        @Header("Authorization") header: String = "KakaoAK ${BuildConfig.KAKAO_REST_API_KEY}",
    ): ApiResult<KakaoLocationInfoResponse>
}
