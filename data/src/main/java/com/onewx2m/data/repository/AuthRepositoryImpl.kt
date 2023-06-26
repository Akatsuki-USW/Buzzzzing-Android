package com.onewx2m.data.repository

import com.onewx2m.data.datasource.LocalAuthDataSource
import com.onewx2m.data.datasource.RemoteAuthDataSource
import com.onewx2m.data.model.toDomain
import com.onewx2m.domain.Outcome
import com.onewx2m.domain.model.Jwt
import com.onewx2m.domain.repository.AuthRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flatMap
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onStart
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val remoteAuthDataSource: RemoteAuthDataSource,
    private val localAuthDataSource: LocalAuthDataSource
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
}

@OptIn(ExperimentalCoroutinesApi::class)
fun <T, R> Flow<Outcome<T>>.flatMapOutcomeSuccess(domainMapper: suspend (T) -> R): Flow<Outcome<R>> {
    return this.flatMapConcat { outcome ->
        flow {
            when (outcome) {
                is Outcome.Loading -> emit(Outcome.Loading)
                is Outcome.Success -> emit(Outcome.Success(domainMapper(outcome.data)))
                is Outcome.Failure -> emit(Outcome.Failure(outcome.error))
            }
        }
    }
}