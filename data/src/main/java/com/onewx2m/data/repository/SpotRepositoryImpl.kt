package com.onewx2m.data.repository

import com.onewx2m.data.datasource.RemoteOtherDataSource
import com.onewx2m.data.datasource.RemoteSpotDataSource
import com.onewx2m.data.extension.flatMapOutcomeSuccess
import com.onewx2m.data.model.toDomain
import com.onewx2m.domain.Outcome
import com.onewx2m.domain.enums.S3Type
import com.onewx2m.domain.model.SpotBookmark
import com.onewx2m.domain.model.SpotDetail
import com.onewx2m.domain.model.SpotList
import com.onewx2m.domain.repository.SpotRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import java.io.File
import javax.inject.Inject

class SpotRepositoryImpl @Inject constructor(
    private val remoteSpotDataSource: RemoteSpotDataSource,
    private val remoteOtherDataSource: RemoteOtherDataSource,
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

    override suspend fun postSpot(
        spotCategoryId: Int,
        locationId: Int,
        title: String,
        address: String,
        content: String,
        imageFiles: List<File>,
    ): Flow<Outcome<SpotDetail>> = flow {
        var profileImageUrls: List<String> = emptyList()

        var isUploadFileFail = false

        if (imageFiles.isNotEmpty()) {
            remoteOtherDataSource.uploadImage(S3Type.SPOT, imageFiles)
                .collect { outcome ->
                    when (outcome) {
                        is Outcome.Success -> {
                            profileImageUrls = outcome.data.map { it.fileUrl }
                        }

                        is Outcome.Failure -> {
                            isUploadFileFail = true
                            emit(Outcome.Failure(outcome.error))
                        }
                    }
                }
        }

        if (isUploadFileFail) return@flow

        remoteSpotDataSource.postSpot(
            spotCategoryId = spotCategoryId,
            locationId = locationId,
            title = title,
            address = address,
            content = content,
            imageFiles = profileImageUrls,
        ).collect { outcome ->
            when (outcome) {
                is Outcome.Success -> emit(Outcome.Success(outcome.data.toDomain()))
                is Outcome.Failure -> emit(Outcome.Failure(outcome.error))
            }
        }
    }

    override suspend fun getSpotBookmarked(cursorId: Int): Flow<Outcome<SpotList>> {
        return remoteSpotDataSource.getSpotBookmarked(cursorId).flatMapOutcomeSuccess { data ->
            data.toDomain()
        }
    }
}
