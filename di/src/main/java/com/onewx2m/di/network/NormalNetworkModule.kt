package com.onewx2m.di.network

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.onewx2m.di.NormalOkHttpClient
import com.onewx2m.di.NormalRetrofit
import com.onewx2m.di.isJsonArray
import com.onewx2m.di.isJsonObject
import com.onewx2m.remote.ResultCallAdapterFactory
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
object NormalNetworkModule {

    private const val BASE_URL = "http://3.104.197.181:8080"
    private const val RETROFIT_TAG = "Retrofit2"

    @Provides
    @NormalOkHttpClient
    fun provideHttpClient(
        loggingInterceptor: HttpLoggingInterceptor
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .readTimeout(10, TimeUnit.SECONDS)
            .connectTimeout(10, TimeUnit.SECONDS)
            .writeTimeout(15, TimeUnit.SECONDS)
            .addInterceptor(loggingInterceptor)
            .build()
    }

    @Provides
    fun provideJsonForLog(): Json {
        return Json { prettyPrint = true }
    }

    @Provides
    fun provideLoggingInterceptor(
        json: Json
    ): HttpLoggingInterceptor {
        val loggingInterceptor = HttpLoggingInterceptor { message ->
            when {
                !message.isJsonObject() && !message.isJsonArray() ->
                    Timber.tag(RETROFIT_TAG).d("CONNECTION INFO -> $message")
                else ->  try {
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
        @NormalOkHttpClient okHttpClient: OkHttpClient
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addCallAdapterFactory(ResultCallAdapterFactory())
            .addConverterFactory(Json.asConverterFactory("application/json".toMediaTypeOrNull()!!))
            .build()
    }
}