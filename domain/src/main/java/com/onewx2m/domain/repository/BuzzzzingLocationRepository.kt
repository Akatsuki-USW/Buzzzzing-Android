package com.onewx2m.domain.repository

import com.onewx2m.domain.Outcome
import com.onewx2m.domain.model.BuzzzzingLocation
import com.onewx2m.domain.model.BuzzzzingLocationBookmark
import kotlinx.coroutines.flow.Flow

interface BuzzzzingLocationRepository {

    suspend fun getBuzzzzingLocation(
        cursorId: Int,
        keyword: String?,
        categoryId: Int?,
        congestionSort: String,
        cursorCongestionLevel: Int?,
    ): Flow<Outcome<BuzzzzingLocation>>

    suspend fun getBuzzzzingLocationTop5(): Flow<Outcome<BuzzzzingLocation>>

    suspend fun locationBookmark(locationId: Int): Flow<Outcome<BuzzzzingLocationBookmark>>
}
