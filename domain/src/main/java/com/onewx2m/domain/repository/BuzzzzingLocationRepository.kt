package com.onewx2m.domain.repository

import com.onewx2m.domain.Outcome
import com.onewx2m.domain.model.BuzzzzingLocation
import com.onewx2m.domain.model.BuzzzzingLocationBookmark
import com.onewx2m.domain.model.BuzzzzingLocationDetailInfo
import com.onewx2m.domain.model.BuzzzzingStatistics
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

    suspend fun getBuzzzzingLocationBookmarked(cursorId: Int): Flow<Outcome<BuzzzzingLocation>>

    suspend fun locationBookmark(locationId: Int): Flow<Outcome<BuzzzzingLocationBookmark>>

    suspend fun getBuzzzzingLocationDetailInfo(locationId: Int): Flow<Outcome<BuzzzzingLocationDetailInfo>>

    suspend fun getBuzzzzingStatistics(locationId: Int, date: String): Flow<Outcome<BuzzzzingStatistics>>
}
