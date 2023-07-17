package com.onewx2m.remote.api

import com.onewx2m.remote.ApiResult
import com.onewx2m.remote.model.ApiResponse
import com.onewx2m.remote.model.response.BuzzzzingLocationResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface BuzzzzingLocationApi {
    companion object {
        const val LOCATION = "locations"
    }

    @GET(LOCATION)
    suspend fun getBuzzzzingLocation(
        @Query("cursorId") cursorId: Int,
        @Query("keyword") keyword: String?,
        @Query("categoryIds") categoryId: Int?,
        @Query("congestionSort") congestionSort: String,
        @Query("cursorCongestionLevel") cursorCongestionLevel: Int?,
    ): ApiResult<ApiResponse<BuzzzzingLocationResponse>>

    @GET("$LOCATION/top")
    suspend fun getBuzzzzingLocationTop5(): ApiResult<ApiResponse<BuzzzzingLocationResponse>>
}
