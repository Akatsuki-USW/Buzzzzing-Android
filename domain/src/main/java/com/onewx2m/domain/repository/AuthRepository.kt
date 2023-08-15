package com.onewx2m.domain.repository

import com.onewx2m.domain.Outcome
import kotlinx.coroutines.flow.Flow

interface AuthRepository {

    suspend fun loginByKakao(oauthAccessToken: String): Flow<Outcome<Unit>>

    suspend fun reIssueBuzzzzingJwt(): Flow<Outcome<Unit>>

    suspend fun getAccessToken(): Flow<String>

    suspend fun getRefreshToken(): Flow<String>

    suspend fun logout(): Flow<Outcome<Unit>>
}
