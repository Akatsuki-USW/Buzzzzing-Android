package com.onewx2m.remote.auth

import com.onewx2m.domain.repository.AuthRepository
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthenticationInterceptor @Inject constructor(private val authRepository: AuthRepository) :
    Interceptor {

    companion object {
        const val RETROFIT_TAG = "Retrofit2"
        const val AUTHORIZATION = "Authorization"
    }

    override fun intercept(chain: Interceptor.Chain): okhttp3.Response {
        val accessToken = runBlocking { authRepository.getAccessToken().first() }

        val request = chain.request().newBuilder()
            .addHeader(AUTHORIZATION, "Bearer $accessToken").build()

        Timber.tag(RETROFIT_TAG).d(
            "AuthenticationInterceptor - intercept() called / accessToken : $accessToken",
        )
        return chain.proceed(request)
    }
}
