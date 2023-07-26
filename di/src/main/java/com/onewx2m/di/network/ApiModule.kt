package com.onewx2m.di.network

import com.onewx2m.di.AuthRetrofit
import com.onewx2m.di.KakaoLocationRetrofit
import com.onewx2m.di.NormalRetrofit
import com.onewx2m.remote.api.AuthApi
import com.onewx2m.remote.api.BuzzzzingLocationApi
import com.onewx2m.remote.api.KakaoLocationApi
import com.onewx2m.remote.api.MediaApi
import com.onewx2m.remote.api.OtherApi
import com.onewx2m.remote.api.SpotApi
import com.onewx2m.remote.api.UserApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.create
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApiModule {

    @Provides
    fun provideAuthApi(@NormalRetrofit retrofit: Retrofit): AuthApi {
        return retrofit.create(AuthApi::class.java)
    }

    @Provides
    fun provideOtherApi(@NormalRetrofit retrofit: Retrofit): OtherApi {
        return retrofit.create(OtherApi::class.java)
    }

    @Provides
    fun provideUserApi(@AuthRetrofit retrofit: Retrofit): UserApi {
        return retrofit.create(UserApi::class.java)
    }

    @Provides
    fun provideMediaApi(@AuthRetrofit retrofit: Retrofit): MediaApi {
        return retrofit.create(MediaApi::class.java)
    }

    @Provides
    fun provideBuzzzzingLocationApi(@AuthRetrofit retrofit: Retrofit): BuzzzzingLocationApi {
        return retrofit.create(BuzzzzingLocationApi::class.java)
    }

    @Provides
    fun provideSpotApi(@AuthRetrofit retrofit: Retrofit): SpotApi {
        return retrofit.create(SpotApi::class.java)
    }

    @Singleton
    @Provides
    fun provideKakaoLocationApi(@KakaoLocationRetrofit retrofit: Retrofit): KakaoLocationApi {
        return retrofit.create(KakaoLocationApi::class.java)
    }
}
