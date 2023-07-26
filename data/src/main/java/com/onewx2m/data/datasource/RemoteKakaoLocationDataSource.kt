package com.onewx2m.data.datasource

import com.onewx2m.data.model.KakaoLocationInfoEntity
import com.onewx2m.domain.Outcome
import kotlinx.coroutines.flow.Flow

interface RemoteKakaoLocationDataSource {

    suspend fun getKakaoLocation(
        query: String,
        page: Int,
    ): Flow<Outcome<KakaoLocationInfoEntity>>
}
