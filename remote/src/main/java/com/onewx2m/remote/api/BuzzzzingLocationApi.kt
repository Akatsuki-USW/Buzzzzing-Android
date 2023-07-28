package com.onewx2m.remote.api

import com.onewx2m.data.model.BuzzzzingStatisticsEntity
import com.onewx2m.domain.Outcome
import com.onewx2m.remote.ApiResult
import com.onewx2m.remote.model.ApiResponse
import com.onewx2m.remote.model.response.BuzzzzingLocationBookmarkResponse
import com.onewx2m.remote.model.response.BuzzzzingLocationDetailInfoResponse
import com.onewx2m.remote.model.response.BuzzzzingLocationResponse
import com.onewx2m.remote.model.response.BuzzzzingStatisticsResponse
import kotlinx.coroutines.flow.Flow
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface BuzzzzingLocationApi {
    companion object {
        const val LOCATION = "locations"
        const val LOCATION_ID = "locationId"
        const val CURSOR_ID = "cursorId"
        const val KEYWORD = "keyword"
        const val CATEGORY_IDS = "categoryIds"
        const val CONGESTION_SORT = "congestionSort"
        const val CURSOR_CONGESTION_LEVEL = "cursorCongestionLevel"
        const val DATE = "date"
        const val BOOKMARK = "bookmarks"
        const val ME = "me"
    }

    @GET(LOCATION)
    suspend fun getBuzzzzingLocation(
        @Query(CURSOR_ID) cursorId: Int,
        @Query(KEYWORD) keyword: String?,
        @Query(CATEGORY_IDS) categoryId: Int?,
        @Query(CONGESTION_SORT) congestionSort: String,
        @Query(CURSOR_CONGESTION_LEVEL) cursorCongestionLevel: Int?,
    ): ApiResult<ApiResponse<BuzzzzingLocationResponse>>

    @GET("$LOCATION/$BOOKMARK/$ME")
    suspend fun getBuzzzzingLocationBookmarked(
        @Query(CURSOR_ID) cursorId: Int,
    ): ApiResult<ApiResponse<BuzzzzingLocationResponse>>

    @GET("$LOCATION/top")
    suspend fun getBuzzzzingLocationTop5(): ApiResult<ApiResponse<BuzzzzingLocationResponse>>

    @POST("$LOCATION/{$LOCATION_ID}/$BOOKMARK")
    suspend fun bookmarkBuzzzzingLocation(
        @Path(LOCATION_ID) locationId: Int,
    ): ApiResult<ApiResponse<BuzzzzingLocationBookmarkResponse>>

    @GET("$LOCATION/{$LOCATION_ID}")
    suspend fun getBuzzzzingLocationDetail(
        @Path(LOCATION_ID) locationId: Int,
    ): ApiResult<ApiResponse<BuzzzzingLocationDetailInfoResponse>>

    @GET("$LOCATION/{$LOCATION_ID}/congestion/daily")
    suspend fun getBuzzzzingStatistics(
        @Path(LOCATION_ID) locationId: Int,
        @Query(DATE) date: String,
    ): ApiResult<ApiResponse<BuzzzzingStatisticsResponse>>
}
