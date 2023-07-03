package com.onewx2m.data.datasource

import com.onewx2m.data.model.VerifyNicknameEntity
import com.onewx2m.domain.Outcome
import kotlinx.coroutines.flow.Flow

interface RemoteOtherDataSource {

    suspend fun verifyNickname(
        nickname: String,
    ): Flow<Outcome<VerifyNicknameEntity>>
}
