package com.onewx2m.domain.repository

import com.onewx2m.domain.Outcome
import com.onewx2m.domain.model.Jwt
import kotlinx.coroutines.flow.Flow

interface AuthRepository {

    suspend fun reIssueBuzzzzingJwt(): Flow<Outcome<Unit>>
}