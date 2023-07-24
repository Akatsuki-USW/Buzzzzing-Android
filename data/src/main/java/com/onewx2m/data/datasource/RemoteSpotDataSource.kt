package com.onewx2m.data.datasource

import com.onewx2m.data.model.SpotListEntity
import com.onewx2m.domain.Outcome
import com.onewx2m.domain.model.SpotList
import kotlinx.coroutines.flow.Flow

interface RemoteSpotDataSource {

    suspend fun getSpotOfLocationList(
        locationId: Int,
        categoryId: Int? = null,
    ): Flow<Outcome<SpotListEntity>>
}
