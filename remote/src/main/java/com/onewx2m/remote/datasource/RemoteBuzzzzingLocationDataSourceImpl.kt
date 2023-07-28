package com.onewx2m.remote.datasource

import com.onewx2m.data.datasource.RemoteBuzzzzingLocationDataSource
import com.onewx2m.data.model.BuzzzzingLocationBookmarkEntity
import com.onewx2m.data.model.BuzzzzingLocationDetailInfoEntity
import com.onewx2m.data.model.BuzzzzingLocationEntity
import com.onewx2m.data.model.BuzzzzingStatisticsEntity
import com.onewx2m.domain.Outcome
import com.onewx2m.domain.exception.BuzzzzingHttpException
import com.onewx2m.domain.exception.NotFoundCongestionStatistics
import com.onewx2m.domain.exception.common.CommonException
import com.onewx2m.remote.api.BuzzzzingLocationApi
import com.onewx2m.remote.model.response.toEntity
import com.onewx2m.remote.onFailure
import com.onewx2m.remote.onSuccess
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class RemoteBuzzzzingLocationDataSourceImpl @Inject constructor(
    private val api: BuzzzzingLocationApi,
) : RemoteBuzzzzingLocationDataSource {

    override suspend fun getBuzzzzingLocation(
        cursorId: Int,
        keyword: String?,
        categoryId: Int?,
        congestionSort: String,
        cursorCongestionLevel: Int?,
    ): Flow<Outcome<BuzzzzingLocationEntity>> = flow {
        api.getBuzzzzingLocation(
            cursorId = cursorId,
            keyword = keyword,
            categoryId = categoryId,
            congestionSort = congestionSort,
            cursorCongestionLevel = cursorCongestionLevel,
        ).onSuccess { body ->
            emit(Outcome.Success(body.data!!.toEntity()))
        }.onFailure {
            emit(Outcome.Failure(it))
        }
    }

    override suspend fun getBuzzzzingLocationTop5(): Flow<Outcome<BuzzzzingLocationEntity>> = flow {
        api.getBuzzzzingLocationTop5()
            .onSuccess { body ->
                emit(Outcome.Success(body.data!!.toEntity()))
            }
            .onFailure {
                emit(Outcome.Failure(it))
            }
    }

    override suspend fun bookmarkBuzzzzingLocation(locationId: Int): Flow<Outcome<BuzzzzingLocationBookmarkEntity>> =
        flow {
            api.bookmarkBuzzzzingLocation(locationId)
                .onSuccess { body ->
                    emit(Outcome.Success(body.data!!.toEntity()))
                }
                .onFailure {
                    emit(Outcome.Failure(it))
                }
        }

    override suspend fun getBuzzzzingLocationDetailInfo(locationId: Int): Flow<Outcome<BuzzzzingLocationDetailInfoEntity>> =
        flow {
            api.getBuzzzzingLocationDetail(locationId)
                .onSuccess { body ->
                    emit(Outcome.Success(body.data!!.toEntity()))
                }
                .onFailure {
                    emit(Outcome.Failure(it))
                }
        }

    override suspend fun getBuzzzzingStatistics(
        locationId: Int,
        date: String,
    ): Flow<Outcome<BuzzzzingStatisticsEntity>> = flow {
        api.getBuzzzzingStatistics(locationId, date)
            .onSuccess { body ->
                emit(Outcome.Success(body.data!!.toEntity()))
            }
            .onFailure { exception ->
                if (exception is BuzzzzingHttpException) {
                    emit(handleStatisticException(exception))
                } else {
                    emit(Outcome.Failure(exception))
                }
            }
    }

    private fun handleStatisticException(
        exception: BuzzzzingHttpException,
    ): Outcome<BuzzzzingStatisticsEntity> = when (exception.httpCode) {
        404 -> Outcome.Failure(NotFoundCongestionStatistics())
        else -> Outcome.Failure(CommonException.UnknownException())
    }

    override suspend fun getBuzzzzingLocationBookmarked(cursorId: Int): Flow<Outcome<BuzzzzingLocationEntity>> =
        flow {
            api.getBuzzzzingLocationBookmarked(cursorId)
                .onSuccess { body ->
                    emit(Outcome.Success(body.data!!.toEntity()))
                }.onFailure {
                    emit(Outcome.Failure(it))
                }
        }
}
