package com.onewx2m.local.datasource

import com.onewx2m.data.datasource.LocalAuthDataSource
import com.onewx2m.domain.model.Jwt
import com.onewx2m.local.datastore.SecurityPreferences
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LocalAuthDataSourceImpl @Inject constructor(
    private val securityPreferences: SecurityPreferences
) : LocalAuthDataSource {
    override fun getAccessToken(): Flow<String> = securityPreferences.flowAccessToken()

    override fun getRefreshToken(): Flow<String> = securityPreferences.flowRefreshToken()

    override suspend fun saveAccessToken(accessToken: String) {
        securityPreferences.setAccessToken(accessToken)
    }

    override suspend fun saveRefreshToken(refreshToken: String) {
        securityPreferences.setRefreshToken(refreshToken)
    }

    override suspend fun saveToken(jwt: Jwt) {
        saveAccessToken(jwt.accessToken)
        saveRefreshToken(jwt.refreshToken)
    }

    override suspend fun clearToken() {
        saveToken(Jwt("", ""))
    }
}