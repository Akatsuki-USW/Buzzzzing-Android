package com.onewx2m.data.datasource

import com.onewx2m.data.model.BuzzzzingLocationBookmarkEntity
import com.onewx2m.data.model.BuzzzzingLocationDetailInfoEntity
import com.onewx2m.data.model.BuzzzzingLocationEntity
import com.onewx2m.data.model.BuzzzzingStatisticsEntity
import com.onewx2m.domain.Outcome
import com.onewx2m.domain.model.BuzzzzingStatistics
import kotlinx.coroutines.flow.Flow

interface RemoteBuzzzzingLocationDataSource {

    suspend fun getBuzzzzingLocation(
        cursorId: Int,
        keyword: String? = null,
        categoryId: Int? = null,
        congestionSort: String,
        cursorCongestionLevel: Int?,
    ): Flow<Outcome<BuzzzzingLocationEntity>>

    suspend fun getBuzzzzingLocationBookmarked(
        cursorId: Int,
    ): Flow<Outcome<BuzzzzingLocationEntity>>

    suspend fun getBuzzzzingLocationTop5(): Flow<Outcome<BuzzzzingLocationEntity>>

    suspend fun bookmarkBuzzzzingLocation(locationId: Int): Flow<Outcome<BuzzzzingLocationBookmarkEntity>>

    suspend fun getBuzzzzingLocationDetailInfo(locationId: Int): Flow<Outcome<BuzzzzingLocationDetailInfoEntity>>

    suspend fun getBuzzzzingStatistics(
        locationId: Int,
        date: String,
    ): Flow<Outcome<BuzzzzingStatisticsEntity>>
}
