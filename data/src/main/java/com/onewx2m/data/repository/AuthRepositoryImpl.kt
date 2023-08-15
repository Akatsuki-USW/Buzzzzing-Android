package com.onewx2m.data.repository

import com.onewx2m.data.datasource.LocalAuthDataSource
import com.onewx2m.data.datasource.RemoteAuthDataSource
import com.onewx2m.data.extension.flatMapOutcomeSuccess
import com.onewx2m.data.model.toDomain
import com.onewx2m.domain.Outcome
import com.onewx2m.domain.repository.AuthRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val remoteAuthDataSource: RemoteAuthDataSource,
    private val localAuthDataSource: LocalAuthDataSource,
) : AuthRepository {

    override suspend fun loginByKakao(oauthAccessToken: String): Flow<Outcome<Unit>> {
        return remoteAuthDataSource.loginByKakao(oauthAccessToken)
            .flatMapOutcomeSuccess { dataModel -> dataModel.toDomain() }
            .flatMapOutcomeSuccess { jwt ->
                localAuthDataSource.saveToken(jwt)
            }
    }

    override suspend fun reIssueBuzzzzingJwt(): Flow<Outcome<Unit>> {
        val refreshToken = localAuthDataSource.getRefreshToken().first()
        return remoteAuthDataSource.reIssueBuzzzzingJwt(refreshToken)
            .flatMapOutcomeSuccess { dataModel -> dataModel.toDomain() }
            .flatMapOutcomeSuccess { jwt ->
                localAuthDataSource.saveToken(jwt)
            }
    }

    override suspend fun getAccessToken() = localAuthDataSource.getAccessToken()

    override suspend fun getRefreshToken() = localAuthDataSource.getRefreshToken()

    override suspend fun logout(): Flow<Outcome<Unit>> {
        return remoteAuthDataSource.logout().flatMapOutcomeSuccess {
            localAuthDataSource.clearToken()
        }
    }
}
