package com.onewx2m.data.datasource

import com.onewx2m.data.model.BuzzzzingLocationBookmarkEntity
import com.onewx2m.data.model.BuzzzzingLocationEntity
import com.onewx2m.domain.Outcome
import kotlinx.coroutines.flow.Flow

interface RemoteBuzzzzingLocationDataSource {

    suspend fun getBuzzzzingLocation(
        cursorId: Int,
        keyword: String? = null,
        categoryId: Int? = null,
        congestionSort: String,
        cursorCongestionLevel: Int?,
    ): Flow<Outcome<BuzzzzingLocationEntity>>

    suspend fun getBuzzzzingLocationTop5(): Flow<Outcome<BuzzzzingLocationEntity>>

    suspend fun bookmarkBuzzzzingLocation(locationId: Int): Flow<Outcome<BuzzzzingLocationBookmarkEntity>>
}
