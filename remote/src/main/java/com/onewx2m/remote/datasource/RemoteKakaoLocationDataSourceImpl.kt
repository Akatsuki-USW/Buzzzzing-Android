package com.onewx2m.remote.datasource

import com.onewx2m.data.datasource.RemoteKakaoLocationDataSource
import com.onewx2m.data.model.KakaoLocationInfoEntity
import com.onewx2m.domain.Outcome
import com.onewx2m.remote.api.KakaoLocationApi
import com.onewx2m.remote.model.response.toEntity
import com.onewx2m.remote.onFailure
import com.onewx2m.remote.onSuccess
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class RemoteKakaoLocationDataSourceImpl @Inject constructor(
    private val api: KakaoLocationApi,
) : RemoteKakaoLocationDataSource {

    override suspend fun getKakaoLocation(
        query: String,
        page: Int,
    ): Flow<Outcome<KakaoLocationInfoEntity>> = flow {
        api.getLocationInfo(
            query = query,
            page = page,
        ).onSuccess { body ->
            emit(Outcome.Success(body.toEntity()))
        }.onFailure {
            emit(Outcome.Failure(it))
        }
    }
}
