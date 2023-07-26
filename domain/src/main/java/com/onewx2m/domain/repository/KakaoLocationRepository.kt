package com.onewx2m.domain.repository

import com.onewx2m.domain.Outcome
import com.onewx2m.domain.model.KakaoLocationInfo
import kotlinx.coroutines.flow.Flow

interface KakaoLocationRepository {
    suspend fun getKakaoLocation(
        query: String,
        page: Int,
    ): Flow<Outcome<KakaoLocationInfo>>
}
