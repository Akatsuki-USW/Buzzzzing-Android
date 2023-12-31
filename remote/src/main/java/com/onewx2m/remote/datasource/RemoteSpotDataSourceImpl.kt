package com.onewx2m.remote.datasource

import com.onewx2m.data.datasource.RemoteSpotDataSource
import com.onewx2m.data.model.SpotBookmarkEntity
import com.onewx2m.data.model.SpotDetailEntity
import com.onewx2m.data.model.SpotListEntity
import com.onewx2m.domain.Outcome
import com.onewx2m.remote.api.SpotApi
import com.onewx2m.remote.model.request.EditSpotRequest
import com.onewx2m.remote.model.request.PostSpotRequest
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

    override suspend fun getAllSpotList(
        cursorId: Int,
        categoryId: Int?,
    ): Flow<Outcome<SpotListEntity>> = flow {
        api.getAllSpotList(
            cursorId = cursorId,
            categoryIds = categoryId,
        ).onSuccess { body ->
            emit(Outcome.Success(body.data!!.toEntity()))
        }.onFailure {
            emit(Outcome.Failure(it))
        }
    }

    override suspend fun postSpot(
        spotCategoryId: Int,
        locationId: Int,
        title: String,
        address: String,
        content: String,
        imageUrls: List<String>,
    ): Flow<Outcome<SpotDetailEntity>> = flow {
        val request = PostSpotRequest(
            title = title,
            address = address,
            content = content,
            imageUrls = imageUrls,
        )
        api.postSpot(
            spotCategoryId = spotCategoryId,
            locationId = locationId,
            request = request,
        )
            .onSuccess { body ->
                emit(Outcome.Success(body.data!!.toEntity()))
            }.onFailure {
                emit(Outcome.Failure(it))
            }
    }

    override suspend fun getSpotBookmarked(cursorId: Int): Flow<Outcome<SpotListEntity>> = flow {
        api.getSpotBookmarked(cursorId).onSuccess { body ->
            emit(Outcome.Success(body.data!!.toEntity()))
        }.onFailure {
            emit(Outcome.Failure(it))
        }
    }

    override suspend fun getSpotDetail(spotId: Int): Flow<Outcome<SpotDetailEntity>> = flow {
        api.getSpotDetail(spotId).onSuccess { body ->
            emit(Outcome.Success(body.data!!.toEntity()))
        }.onFailure {
            emit(Outcome.Failure(it))
        }
    }

    override suspend fun editSpot(
        spotId: Int,
        title: String,
        address: String,
        content: String,
        imageUrls: List<String>,
        locationId: Int,
        spotCategoryId: Int,
    ): Flow<Outcome<SpotDetailEntity>> = flow {
        val request = EditSpotRequest(
            title = title,
            address = address,
            content = content,
            imageUrls = imageUrls,
            locationId = locationId,
            spotCategoryId = spotCategoryId,
        )
        api.editSpot(spotId = spotId, request = request).onSuccess { body ->
            emit(Outcome.Success(body.data!!.toEntity()))
        }.onFailure {
            emit(Outcome.Failure(it))
        }
    }

    override suspend fun deleteSpot(spotId: Int): Flow<Outcome<Unit>> = flow {
        api.deleteSpot(spotId).onSuccess {
            emit(Outcome.Success(it.data!!))
        }.onFailure { exception ->
            emit(Outcome.Failure(exception))
        }
    }
}
