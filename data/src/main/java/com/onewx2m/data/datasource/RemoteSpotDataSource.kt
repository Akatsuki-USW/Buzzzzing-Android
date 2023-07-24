package com.onewx2m.data.datasource

import com.onewx2m.data.model.SpotBookmarkEntity
import com.onewx2m.data.model.SpotListEntity
import com.onewx2m.domain.Outcome
import kotlinx.coroutines.flow.Flow

interface RemoteSpotDataSource {

    suspend fun getSpotOfLocationList(
        cursorId: Int,
        locationId: Int,
        categoryId: Int? = null,
    ): Flow<Outcome<SpotListEntity>>

    suspend fun bookmarkSpot(spotId: Int): Flow<Outcome<SpotBookmarkEntity>>
}
