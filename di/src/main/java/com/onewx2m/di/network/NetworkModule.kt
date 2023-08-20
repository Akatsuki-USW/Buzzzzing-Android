package com.onewx2m.di.network

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.onewx2m.di.AuthOkHttpClient
import com.onewx2m.di.AuthRetrofit
import com.onewx2m.di.KakaoLocationRetrofit
import com.onewx2m.di.NormalOkHttpClient
import com.onewx2m.di.NormalRetrofit
import com.onewx2m.di.isJsonArray
import com.onewx2m.di.isJsonObject
import com.onewx2m.remote.ResultCallAdapterFactory
import com.onewx2m.remote.auth.AuthenticationInterceptor
import com.onewx2m.remote.auth.TokenAuthenticator
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import timber.log.Timber
import java.util.concurrent.TimeUnit

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    private const val BASE_URL = "https://api.bokjak.com"
    private const val KAKAO_LOCATION_BASE_URL = "https://dapi.kakao.com"
    private const val RETROFIT_TAG = "Retrofit2"

    @Provides
    @NormalOkHttpClient
    fun provideNormalHttpClient(
        loggingInterceptor: HttpLoggingInterceptor,
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .readTimeout(10, TimeUnit.SECONDS)
            .connectTimeout(10, TimeUnit.SECONDS)
            .writeTimeout(15, TimeUnit.SECONDS)
            .addInterceptor(loggingInterceptor)
            .build()
    }

    @Provides
    fun provideJson(): Json {
        return Json {
            prettyPrint = true
            coerceInputValues = true
            ignoreUnknownKeys = true
        }
    }

    @Provides
    fun provideLoggingInterceptor(
        json: Json,
    ): HttpLoggingInterceptor {
        val loggingInterceptor = HttpLoggingInterceptor { message ->
            when {
                !message.isJsonObject() && !message.isJsonArray() ->
                    Timber.tag(RETROFIT_TAG).d("CONNECTION INFO -> $message")

                else -> try {
                    val prettyMessage = json.encodeToString(Json.parseToJsonElement(message))
                    Timber.tag(RETROFIT_TAG).d(prettyMessage)
                } catch (e: Exception) {
                    Timber.tag(RETROFIT_TAG).d(message)
                }
            }
        }
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        return loggingInterceptor
    }

    @Provides
    @NormalRetrofit
    fun provideNormalRetrofit(
        @NormalOkHttpClient okHttpClient: OkHttpClient,
        json: Json,
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addCallAdapterFactory(ResultCallAdapterFactory())
            .addConverterFactory(json.asConverterFactory("application/json".toMediaTypeOrNull()!!))
            .build()
    }

    @Provides
    @AuthOkHttpClient
    fun provideAuthHttpClient(
        authenticator: TokenAuthenticator,
        authenticationInterceptor: AuthenticationInterceptor,
        loggingInterceptor: HttpLoggingInterceptor,
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .readTimeout(10, TimeUnit.SECONDS)
            .connectTimeout(10, TimeUnit.SECONDS)
            .writeTimeout(15, TimeUnit.SECONDS)
            .addInterceptor(loggingInterceptor)
            .addInterceptor(authenticationInterceptor)
            .authenticator(authenticator)
            .build()
    }

    @Provides
    @AuthRetrofit
    fun provideAuthRetrofit(
        @AuthOkHttpClient okHttpClient: OkHttpClient,
        json: Json,
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addCallAdapterFactory(ResultCallAdapterFactory())
            .addConverterFactory(json.asConverterFactory("application/json".toMediaTypeOrNull()!!))
            .build()
    }

    @Provides
    @KakaoLocationRetrofit
    fun provideKakaoLocationRetrofit(
        @NormalOkHttpClient okHttpClient: OkHttpClient,
        json: Json,
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(KAKAO_LOCATION_BASE_URL)
            .client(okHttpClient)
            .addCallAdapterFactory(ResultCallAdapterFactory())
            .addConverterFactory(json.asConverterFactory("application/json".toMediaTypeOrNull()!!))
            .build()
    }
}
