package com.onewx2m.data.datasource

import com.onewx2m.data.model.BuzzzzingLocationEntity
import com.onewx2m.data.model.UserInfoEntity
import com.onewx2m.domain.Outcome
import kotlinx.coroutines.flow.Flow

interface RemoteBuzzzzingLocationDataSource {

    suspend fun getBuzzzzingLocation(
        cursorId: Int,
        keyword: String? = null,
        categoryId: Long? = null,
        congestionSort: String,
        cursorCongestionLevel: Int,
    ): Flow<Outcome<BuzzzzingLocationEntity>>
}
