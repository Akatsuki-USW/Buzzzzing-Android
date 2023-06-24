package com.onewx2m.data.datasource

import com.onewx2m.domain.model.Jwt
import kotlinx.coroutines.flow.Flow

interface LocalAuthDataSource {

    fun getAccessToken(): Flow<String>
    fun getRefreshToken(): Flow<String>

    suspend fun saveAccessToken(accessToken: String)
    suspend fun saveRefreshToken(refreshToken: String)

    suspend fun saveToken(jwt: Jwt)

    suspend fun clearToken()
}
