package com.onewx2m.data.repository

import com.onewx2m.data.datasource.RemoteSpotDataSource
import com.onewx2m.data.extension.flatMapOutcomeSuccess
import com.onewx2m.data.model.toDomain
import com.onewx2m.domain.Outcome
import com.onewx2m.domain.model.SpotBookmark
import com.onewx2m.domain.model.SpotList
import com.onewx2m.domain.repository.SpotRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SpotRepositoryImpl @Inject constructor(
    private val remoteSpotDataSource: RemoteSpotDataSource,
) : SpotRepository {
    override suspend fun getSpotOfLocationList(
        cursorId: Int,
        locationId: Int,
        categoryId: Int?,
    ): Flow<Outcome<SpotList>> {
        return remoteSpotDataSource.getSpotOfLocationList(cursorId, locationId, categoryId)
            .flatMapOutcomeSuccess { data ->
                data.toDomain()
            }
    }

    override suspend fun spotBookmark(spotId: Int): Flow<Outcome<SpotBookmark>> {
        return remoteSpotDataSource.bookmarkSpot(spotId)
            .flatMapOutcomeSuccess { data -> data.toDomain() }
    }

    override suspend fun getAllSpotList(cursorId: Int, categoryId: Int?): Flow<Outcome<SpotList>> {
        return remoteSpotDataSource.getAllSpotList(cursorId, categoryId)
            .flatMapOutcomeSuccess { data ->
                data.toDomain()
            }
    }
}