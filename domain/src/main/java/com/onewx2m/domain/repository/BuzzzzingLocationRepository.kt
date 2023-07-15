package com.onewx2m.domain.repository

import com.onewx2m.domain.Outcome
import com.onewx2m.domain.model.BuzzzzingLocation
import kotlinx.coroutines.flow.Flow

interface BuzzzzingLocationRepository {

    suspend fun getBuzzzzingLocation(
        cursorId: Int,
        keyword: String? = null,
        categoryId: Long? = null,
        congestionSort: String,
        cursorCongestionLevel: Int,
    ): Flow<Outcome<BuzzzzingLocation>>
}
