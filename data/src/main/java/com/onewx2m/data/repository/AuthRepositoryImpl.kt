package com.onewx2m.data.repository

import com.onewx2m.data.datasource.RemoteAuthDataSource
import com.onewx2m.data.model.toDomain
import com.onewx2m.domain.Outcome
import com.onewx2m.domain.model.Jwt
import com.onewx2m.domain.repository.AuthRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flatMap
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onStart
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val remoteAuthDataSource: RemoteAuthDataSource
) : AuthRepository {

    override suspend fun reIssueBuzzzzingJwt(refreshToken: String): Flow<Outcome<Jwt>> =
        remoteAuthDataSource.reIssueBuzzzzingJwt(refreshToken).flatMapDataToDomainOutcome { dataModel ->
            dataModel.toDomain()
        }

}

@OptIn(ExperimentalCoroutinesApi::class)
fun <DATA, DOMAIN> Flow<Outcome<DATA>>.flatMapDataToDomainOutcome(domainMapper: (DATA) -> DOMAIN): Flow<Outcome<DOMAIN>> {
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