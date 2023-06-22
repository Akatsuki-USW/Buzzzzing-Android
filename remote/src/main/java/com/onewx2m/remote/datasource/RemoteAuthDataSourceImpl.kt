package com.onewx2m.remote.datasource

import com.onewx2m.data.datasource.RemoteAuthDataSource
import com.onewx2m.data.model.JwtEntity
import com.onewx2m.domain.Outcome
import com.onewx2m.remote.api.AuthApi
import com.onewx2m.remote.model.request.JwtReIssueRequest
import com.onewx2m.remote.wrapOutcomeLoadingFailure
import com.onewx2m.remote.model.response.toEntity
import com.onewx2m.remote.onFailure
import com.onewx2m.remote.onSuccess
import com.onewx2m.remote.toBuzzzzingException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import timber.log.Timber
import javax.inject.Inject

class RemoteAuthDataSourceImpl @Inject constructor(
    private val api: AuthApi
) : RemoteAuthDataSource {
    override suspend fun login(
        oauthAccessToken: String,
        socialType: String
    ): Flow<Outcome<JwtEntity>> {
        TODO("Not yet implemented")
    }

    override suspend fun reIssueBuzzzzingJwt(refreshToken: String) =
        flow<Outcome<JwtEntity>> {
            api.reIssueBuzzzzingJwt(JwtReIssueRequest(refreshToken))
                .onSuccess { body ->
                    emit(Outcome.Success(body.data!!.toEntity()))
                }.onFailure { error ->
                    val exception = error.toBuzzzzingException()
                    emit(Outcome.Failure(exception))
                }
        }.wrapOutcomeLoadingFailure()
}