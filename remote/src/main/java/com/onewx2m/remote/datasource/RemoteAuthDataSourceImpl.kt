package com.onewx2m.remote.datasource

import com.google.firebase.messaging.FirebaseMessaging
import com.onewx2m.data.datasource.RemoteAuthDataSource
import com.onewx2m.data.model.JwtEntity
import com.onewx2m.domain.Outcome
import com.onewx2m.domain.enums.SnsType
import com.onewx2m.domain.exception.BuzzzzingHttpException
import com.onewx2m.domain.exception.NeedSignUpException
import com.onewx2m.domain.exception.RevokeUntilMonthUserException
import com.onewx2m.domain.exception.common.CommonException
import com.onewx2m.remote.KotlinSerializationUtil
import com.onewx2m.remote.api.AuthApi
import com.onewx2m.remote.model.ApiResponse
import com.onewx2m.remote.model.request.JwtReIssueRequest
import com.onewx2m.remote.model.request.LoginRequest
import com.onewx2m.remote.model.response.SignTokenResponse
import com.onewx2m.remote.model.response.toEntity
import com.onewx2m.remote.onFailure
import com.onewx2m.remote.onSuccess
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class RemoteAuthDataSourceImpl @Inject constructor(
    private val api: AuthApi,
) : RemoteAuthDataSource {

    override suspend fun loginByKakao(oauthAccessToken: String): Flow<Outcome<JwtEntity>> =
        flow {
            api.login(
                LoginRequest(
                    oauthAccessToken = oauthAccessToken,
                    socialType = SnsType.KAKAO.name,
                    fcmToken = FirebaseMessaging.getInstance().token.await(),
                ),
            )
                .onSuccess { body ->
                    emit(Outcome.Success(body.data!!.toEntity()))
                }
                .onFailure { exception ->
                    when (exception) {
                        is BuzzzzingHttpException -> emit(handleLoginByKakaoException(exception))
                        else -> emit(Outcome.Failure(exception))
                    }
                }
        }

    private fun handleLoginByKakaoException(
        exception: BuzzzzingHttpException,
    ): Outcome<JwtEntity> = when (exception.customStatusCode) {
        1040 -> Outcome.Failure(NeedSignUpException(getSignToken(exception.httpBody)))
        2050 -> Outcome.Failure(RevokeUntilMonthUserException())
        else -> Outcome.Failure(CommonException.UnknownException())
    }

    private fun getSignToken(httpBody: String) =
        KotlinSerializationUtil.json.decodeFromString<ApiResponse<SignTokenResponse>>(
            httpBody,
        ).data!!.signToken

    override suspend fun reIssueBuzzzzingJwt(refreshToken: String) =
        flow<Outcome<JwtEntity>> {
            api.reIssueBuzzzzingJwt(JwtReIssueRequest(refreshToken))
                .onSuccess { body ->
                    emit(Outcome.Success(body.data!!.toEntity()))
                }.onFailure { exception ->
                    emit(Outcome.Failure(exception))
                }
        }

    override suspend fun logout(): Flow<Outcome<Unit>> = flow {
        api.logout().onSuccess {
            emit(Outcome.Success(it.data!!))
        }.onFailure { exception ->
            emit(Outcome.Failure(exception))
        }
    }
}
