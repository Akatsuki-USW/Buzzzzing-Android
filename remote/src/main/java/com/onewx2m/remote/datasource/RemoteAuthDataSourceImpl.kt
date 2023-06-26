package com.onewx2m.remote.datasource

import com.onewx2m.data.datasource.RemoteAuthDataSource
import com.onewx2m.data.model.JwtEntity
import com.onewx2m.domain.Outcome
import com.onewx2m.domain.enums.SnsType
import com.onewx2m.domain.exception.BuzzzzingHttpException
import com.onewx2m.domain.exception.SignTokenException
import com.onewx2m.remote.KotlinSerializationUtil
import com.onewx2m.remote.api.AuthApi
import com.onewx2m.remote.model.ApiResponse
import com.onewx2m.remote.model.ErrorResponse
import com.onewx2m.remote.model.request.JwtReIssueRequest
import com.onewx2m.remote.model.request.LoginRequest
import com.onewx2m.remote.model.response.SignTokenResponse
import com.onewx2m.remote.wrapOutcomeLoadingFailure
import com.onewx2m.remote.model.response.toEntity
import com.onewx2m.remote.onFailure
import com.onewx2m.remote.onSuccess
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.flow
import kotlinx.serialization.json.Json
import javax.inject.Inject

class RemoteAuthDataSourceImpl @Inject constructor(
    private val api: AuthApi
) : RemoteAuthDataSource {

    override suspend fun loginByKakao(oauthAccessToken: String): Flow<Outcome<JwtEntity>> =
        flow<Outcome<JwtEntity>> {
            api.login(LoginRequest(oauthAccessToken = oauthAccessToken, socialType = SnsType.KAKAO.name))
                .onSuccess { body ->
                    emit(Outcome.Success(body.data!!.toEntity()))
                }.onFailure { exception ->
                    handleLoginByKakaoException(exception)
                }
        }.wrapOutcomeLoadingFailure()

    private suspend fun FlowCollector<Outcome<JwtEntity>>.handleLoginByKakaoException(
        exception: RuntimeException
    ) {
        if (exception is BuzzzzingHttpException && exception.customStatusCode == 1040) {
            val signTokenResponse =
                KotlinSerializationUtil.json.decodeFromString<ApiResponse<SignTokenResponse>>(
                    exception.httpBody
                ).data!!
            emit(Outcome.Failure(SignTokenException(signTokenResponse.signToken)))
        } else emit(Outcome.Failure(exception))
    }

    override suspend fun reIssueBuzzzzingJwt(refreshToken: String) =
        flow<Outcome<JwtEntity>> {
            api.reIssueBuzzzzingJwt(JwtReIssueRequest(refreshToken))
                .onSuccess { body ->
                    emit(Outcome.Success(body.data!!.toEntity()))
                }.onFailure { exception ->
                    emit(Outcome.Failure(exception))
                }
        }.wrapOutcomeLoadingFailure()
}