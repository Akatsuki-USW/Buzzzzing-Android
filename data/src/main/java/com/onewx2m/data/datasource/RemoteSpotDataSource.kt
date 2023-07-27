package com.onewx2m.data.datasource

import com.onewx2m.data.model.SpotBookmarkEntity
import com.onewx2m.data.model.SpotDetailEntity
import com.onewx2m.data.model.SpotListEntity
import com.onewx2m.domain.Outcome
import com.onewx2m.domain.model.SpotDetail
import kotlinx.coroutines.flow.Flow
import java.io.File

interface RemoteSpotDataSource {

    suspend fun getSpotOfLocationList(
        cursorId: Int,
        locationId: Int,
        categoryId: Int? = null,
    ): Flow<Outcome<SpotListEntity>>

    suspend fun bookmarkSpot(spotId: Int): Flow<Outcome<SpotBookmarkEntity>>

    suspend fun getAllSpotList(
        cursorId: Int,
        categoryId: Int? = null,
    ): Flow<Outcome<SpotListEntity>>

    suspend fun postSpot(
        spotCategoryId: Int,
        locationId: Int,
        title: String,
        address: String,
        content: String,
        imageFiles: List<String>,
    ): Flow<Outcome<SpotDetailEntity>>
}
