package com.onewx2m.remote.datasource

import com.onewx2m.data.datasource.RemoteBuzzzzingLocationDataSource
import com.onewx2m.data.model.BuzzzzingLocationEntity
import com.onewx2m.domain.Outcome
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
}
