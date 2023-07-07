package com.onewx2m.remote.auth

import com.onewx2m.domain.Outcome
import com.onewx2m.domain.repository.AuthRepository
import com.onewx2m.domain.usecase.ReissueJwtUseCase
import com.onewx2m.remote.auth.AuthenticationInterceptor.Companion.AUTHORIZATION
import com.onewx2m.remote.auth.AuthenticationInterceptor.Companion.RETROFIT_TAG
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.lastOrNull
import kotlinx.coroutines.flow.zip
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import okhttp3.Authenticator
import okhttp3.Request
import okhttp3.Route
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TokenAuthenticator @Inject constructor(
    private val authRepository: AuthRepository,
    private val reissueJwtUseCase: ReissueJwtUseCase,
) : Authenticator {
    private val mutex = Mutex()

    override fun authenticate(route: Route?, response: okhttp3.Response): Request? = runBlocking {
        val (accessTokenNotVerify, refreshTokenNotVerify) = runBlocking {
            with(authRepository) {
                getAccessToken().zip(getRefreshToken()) { accessToken, refreshToken ->
                    (accessToken to refreshToken)
                }.first()
            }
        }

        mutex.withLock {
            if (verifyTokenIsRefreshed(accessTokenNotVerify, refreshTokenNotVerify)) {
                Timber.tag(RETROFIT_TAG)
                    .d("TokenAuthenticator - authenticate() called / 중단된 API 재요청")
                response.request
                    .newBuilder()
                    .removeHeader(AUTHORIZATION)
                    .header(
                        AUTHORIZATION,
                        "Bearer " + authRepository.getAccessToken().first(),
                    )
                    .build()
            } else {
                null
            }
        }
    }

    private suspend fun verifyTokenIsRefreshed(
        accessTokenNotVerify: String,
        refreshTokenNotVerify: String,
    ): Boolean {
        val latestAccessToken = authRepository.getAccessToken().first()
        val isAccessTokenAlreadyRefreshed = accessTokenNotVerify != latestAccessToken

        return if (isAccessTokenAlreadyRefreshed) {
            true
        } else {
            Timber.tag(RETROFIT_TAG)
                .d("TokenAuthenticator - authenticate() called / 토큰 만료. 토큰 Refresh 요청: $refreshTokenNotVerify")

            val reissueOutcome = reissueJwtUseCase().lastOrNull()

            return if (reissueOutcome != Outcome.Success(Unit)) {
                Timber.tag(RETROFIT_TAG)
                    .d("TokenAuthenticator - verifyTokenIsRefreshed() called / 토큰 갱신 실패.")
                false
            } else {
                true
            }
        }
    }
}
