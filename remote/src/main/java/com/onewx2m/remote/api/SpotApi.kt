package com.onewx2m.remote.api

import com.onewx2m.remote.ApiResult
import com.onewx2m.remote.api.BuzzzzingLocationApi.Companion.CATEGORY_IDS
import com.onewx2m.remote.api.BuzzzzingLocationApi.Companion.LOCATION
import com.onewx2m.remote.api.BuzzzzingLocationApi.Companion.LOCATION_ID
import com.onewx2m.remote.model.ApiResponse
import com.onewx2m.remote.model.response.SpotListResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface SpotApi {
    companion object {
        const val SPOT = "spots"
    }

    @GET("$LOCATION/{$LOCATION_ID}/$SPOT")
    suspend fun getSpotOfLocationList(
        @Path(LOCATION_ID) locationId: Int,
        @Query(CATEGORY_IDS) categoryIds: Int?,
    ): ApiResult<ApiResponse<SpotListResponse>>
}
