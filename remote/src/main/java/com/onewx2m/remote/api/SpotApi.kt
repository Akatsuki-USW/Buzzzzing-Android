package com.onewx2m.remote.api

import com.onewx2m.remote.ApiResult
import com.onewx2m.remote.api.BuzzzzingLocationApi.Companion.BOOKMARK
import com.onewx2m.remote.api.BuzzzzingLocationApi.Companion.CATEGORY_IDS
import com.onewx2m.remote.api.BuzzzzingLocationApi.Companion.CURSOR_ID
import com.onewx2m.remote.api.BuzzzzingLocationApi.Companion.LOCATION
import com.onewx2m.remote.api.BuzzzzingLocationApi.Companion.LOCATION_ID
import com.onewx2m.remote.api.BuzzzzingLocationApi.Companion.ME
import com.onewx2m.remote.model.ApiResponse
import com.onewx2m.remote.model.request.EditSpotRequest
import com.onewx2m.remote.model.request.PostSpotRequest
import com.onewx2m.remote.model.response.SpotBookmarkResponse
import com.onewx2m.remote.model.response.SpotDetailResponse
import com.onewx2m.remote.model.response.SpotListResponse
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

interface SpotApi {
    companion object {
        const val SPOT = "spots"
        const val SPOT_ID = "spotId"
        const val SPOT_CATEGORY_ID = "spotCategoryId"
    }

    @GET("$LOCATION/{$LOCATION_ID}/$SPOT")
    suspend fun getSpotOfLocationList(
        @Path(LOCATION_ID) locationId: Int,
        @Query(CURSOR_ID) cursorId: Int,
        @Query(CATEGORY_IDS) categoryIds: Int?,
    ): ApiResult<ApiResponse<SpotListResponse>>

    @GET("$LOCATION/$SPOT/$BOOKMARK/$ME")
    suspend fun getSpotBookmarked(
        @Query(CURSOR_ID) cursorId: Int,
    ): ApiResult<ApiResponse<SpotListResponse>>

    @POST("$LOCATION/$SPOT/{$SPOT_ID}/$BOOKMARK")
    suspend fun bookmarkSpot(
        @Path(SPOT_ID) spotId: Int,
    ): ApiResult<ApiResponse<SpotBookmarkResponse>>

    @GET("$LOCATION/$SPOT")
    suspend fun getAllSpotList(
        @Query(CURSOR_ID) cursorId: Int,
        @Query(CATEGORY_IDS) categoryIds: Int?,
    ): ApiResult<ApiResponse<SpotListResponse>>

    @POST("$LOCATION/{$LOCATION_ID}/spot-categories/{$SPOT_CATEGORY_ID}/$SPOT")
    suspend fun postSpot(
        @Path(LOCATION_ID) locationId: Int,
        @Path(SPOT_CATEGORY_ID) spotCategoryId: Int,
        @Body request: PostSpotRequest,
    ): ApiResult<ApiResponse<SpotDetailResponse>>

    @GET("$LOCATION/$SPOT/{$SPOT_ID}")
    suspend fun getSpotDetail(
        @Path(SPOT_ID) spotId: Int,
    ): ApiResult<ApiResponse<SpotDetailResponse>>

    @PUT("$LOCATION/$SPOT/{$SPOT_ID}")
    suspend fun editSpot(
        @Path(SPOT_ID) spotId: Int,
        @Body request: EditSpotRequest,
    ): ApiResult<ApiResponse<SpotDetailResponse>>

    @DELETE("$LOCATION/$SPOT/{$SPOT_ID}")
    suspend fun deleteSpot(
        @Path(SPOT_ID) spotId: Int,
    ): ApiResult<ApiResponse<Unit>>
}
