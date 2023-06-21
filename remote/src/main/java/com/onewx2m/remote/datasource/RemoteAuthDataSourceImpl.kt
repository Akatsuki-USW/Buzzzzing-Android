package com.onewx2m.remote.datasource

import com.onewx2m.data.datasource.RemoteAuthDataSource
import com.onewx2m.data.model.BuzzzzingJwtEntity
import com.onewx2m.domain.Outcome
import com.onewx2m.domain.exception.HttpException
import com.onewx2m.remote.api.AuthApi
import com.onewx2m.remote.wrapOutcomeLoadingFailure
import com.onewx2m.remote.model.response.toEntity
import com.onewx2m.remote.onFailure
import com.onewx2m.remote.onSuccess
import com.onewx2m.remote.toBuzzzzingException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class RemoteAuthDataSourceImpl(
    private val api: AuthApi
) : RemoteAuthDataSource {
    override suspend fun login(
        oauthAccessToken: String,
        socialType: String
    ): Flow<Outcome<BuzzzzingJwtEntity>> {
        TODO("Not yet implemented")
    }

    override suspend fun reIssueBuzzzzingJwt(refreshToken: String) =
        flow<Outcome<BuzzzzingJwtEntity>> {
            api.reIssueBuzzzzingJwt()
                .onSuccess { data ->
                    emit(Outcome.Success(data.toEntity()))
                }.onFailure { error ->
                    val exception = error.toBuzzzzingException()
                    emit(Outcome.Failure(exception))
                }
        }.wrapOutcomeLoadingFailure()
}