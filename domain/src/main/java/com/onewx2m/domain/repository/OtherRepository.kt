package com.onewx2m.domain.repository

import com.onewx2m.domain.Outcome
import com.onewx2m.domain.model.VerifyNickname
import kotlinx.coroutines.flow.Flow

interface OtherRepository {

    suspend fun verifyNickname(nickname: String): Flow<Outcome<VerifyNickname>>
}
