package com.onewx2m.di.network

import com.onewx2m.di.NormalRetrofit
import com.onewx2m.remote.api.AuthApi
import com.onewx2m.remote.api.OtherApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.create

@Module
@InstallIn(SingletonComponent::class)
object ApiModule {

    @Provides
    fun provideAuthApi(@NormalRetrofit retrofit: Retrofit): AuthApi {
        return retrofit.create(AuthApi::class.java)
    }

    @Provides
    fun provideUserApi(@NormalRetrofit retrofit: Retrofit): OtherApi {
        return retrofit.create(OtherApi::class.java)
    }
}
