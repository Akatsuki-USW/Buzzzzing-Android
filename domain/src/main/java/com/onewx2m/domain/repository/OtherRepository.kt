package com.onewx2m.domain.repository

import com.onewx2m.domain.Outcome
import com.onewx2m.domain.model.VerifyNickname
import kotlinx.coroutines.flow.Flow
import java.io.File

interface OtherRepository {

    suspend fun verifyNickname(nickname: String): Flow<Outcome<VerifyNickname>>

    suspend fun signUp(
        file: File?,
        signToken: String,
        nickname: String,
        email: String,
    ): Flow<Outcome<Unit>>
}
