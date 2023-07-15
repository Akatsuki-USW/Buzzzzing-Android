package com.onewx2m.data.repository

import com.onewx2m.data.datasource.RemoteBuzzzzingLocationDataSource
import com.onewx2m.data.extension.flatMapOutcomeSuccess
import com.onewx2m.data.model.toDomain
import com.onewx2m.domain.Outcome
import com.onewx2m.domain.model.BuzzzzingLocation
import com.onewx2m.domain.repository.BuzzzzingLocationRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class BuzzzzingLocationRepositoryImpl @Inject constructor(
    private val remoteBuzzzzingLocationDataSource: RemoteBuzzzzingLocationDataSource,
) : BuzzzzingLocationRepository {
    override suspend fun getBuzzzzingLocation(
        cursorId: Int,
        keyword: String?,
        categoryId: Long?,
        congestionSort: String,
        cursorCongestionLevel: Int,
    ): Flow<Outcome<BuzzzzingLocation>> {
        return remoteBuzzzzingLocationDataSource.getBuzzzzingLocation(
            cursorId = cursorId,
            keyword = keyword,
            categoryId = categoryId,
            congestionSort = congestionSort,
            cursorCongestionLevel = cursorCongestionLevel,
        ).flatMapOutcomeSuccess { data -> data.toDomain() }
    }
}