package com.onewx2m.remote.datasource

import com.onewx2m.data.datasource.RemoteSpotDataSource
import com.onewx2m.data.model.SpotBookmarkEntity
import com.onewx2m.data.model.SpotListEntity
import com.onewx2m.domain.Outcome
import com.onewx2m.remote.api.SpotApi
import com.onewx2m.remote.model.response.toEntity
import com.onewx2m.remote.onFailure
import com.onewx2m.remote.onSuccess
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class RemoteSpotDataSourceImpl @Inject constructor(
    private val api: SpotApi,
) : RemoteSpotDataSource {
    override suspend fun getSpotOfLocationList(
        cursorId: Int,
        locationId: Int,
        categoryId: Int?,
    ): Flow<Outcome<SpotListEntity>> = flow {
        api.getSpotOfLocationList(
            cursorId = cursorId,
            locationId = locationId,
            categoryIds = categoryId,
        ).onSuccess { body ->
            emit(Outcome.Success(body.data!!.toEntity()))
        }.onFailure {
            emit(Outcome.Failure(it))
        }
    }

    override suspend fun bookmarkSpot(spotId: Int): Flow<Outcome<SpotBookmarkEntity>> = flow {
        api.bookmarkSpot(spotId)
            .onSuccess { body ->
                emit(Outcome.Success(body.data!!.toEntity()))
            }
            .onFailure {
                emit(Outcome.Failure(it))
            }
    }
}
