package com.onewx2m.data.repository

import com.onewx2m.data.datasource.RemoteKakaoLocationDataSource
import com.onewx2m.data.extension.flatMapOutcomeSuccess
import com.onewx2m.data.model.toDomain
import com.onewx2m.domain.Outcome
import com.onewx2m.domain.model.KakaoLocationInfo
import com.onewx2m.domain.repository.KakaoLocationRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class KakaoLocationRepositoryImpl @Inject constructor(
    private val kakaoLocationDataSource: RemoteKakaoLocationDataSource,
) : KakaoLocationRepository {

    override suspend fun getKakaoLocation(
        query: String,
        page: Int,
    ): Flow<Outcome<KakaoLocationInfo>> {
        return kakaoLocationDataSource.getKakaoLocation(query = query, page = page)
            .flatMapOutcomeSuccess { data ->
                data.toDomain()
            }
    }
}
