@file:Suppress("UnnecessaryAbstractClass")

package com.onewx2m.di

import com.onewx2m.data.repository.AuthRepositoryImpl
import com.onewx2m.data.repository.BuzzzzingLocationRepositoryImpl
import com.onewx2m.data.repository.CategoryRepositoryImpl
import com.onewx2m.data.repository.KakaoLocationRepositoryImpl
import com.onewx2m.data.repository.NotificationRepositoryImpl
import com.onewx2m.data.repository.OtherRepositoryImpl
import com.onewx2m.data.repository.SpotCommentRepositoryImpl
import com.onewx2m.data.repository.SpotRepositoryImpl
import com.onewx2m.data.repository.UserRepositoryImpl
import com.onewx2m.domain.repository.AuthRepository
import com.onewx2m.domain.repository.BuzzzzingLocationRepository
import com.onewx2m.domain.repository.CategoryRepository
import com.onewx2m.domain.repository.KakaoLocationRepository
import com.onewx2m.domain.repository.NotificationRepository
import com.onewx2m.domain.repository.OtherRepository
import com.onewx2m.domain.repository.SpotCommentRepository
import com.onewx2m.domain.repository.SpotRepository
import com.onewx2m.domain.repository.UserRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun bindAuthRepository(
        authRepositoryImpl: AuthRepositoryImpl,
    ): AuthRepository

    @Binds
    abstract fun bindOtherRepository(
        otherRepositoryImpl: OtherRepositoryImpl,
    ): OtherRepository

    @Binds
    abstract fun bindUserRepository(
        userRepositoryImpl: UserRepositoryImpl,
    ): UserRepository

    @Binds
    abstract fun bindCategoryRepository(
        categoryRepositoryImpl: CategoryRepositoryImpl,
    ): CategoryRepository

    @Binds
    abstract fun bindBuzzzzingLocationRepository(
        buzzzzingLocationRepositoryImpl: BuzzzzingLocationRepositoryImpl,
    ): BuzzzzingLocationRepository

    @Binds
    abstract fun bindSpotRepository(
        spotRepositoryImpl: SpotRepositoryImpl,
    ): SpotRepository

    @Binds
    abstract fun bindKakaoRepository(
        kakaoLocationRepositoryImpl: KakaoLocationRepositoryImpl,
    ): KakaoLocationRepository

    @Binds
    abstract fun bindSpotCommentRepository(
        spotCommentRepositoryImpl: SpotCommentRepositoryImpl,
    ): SpotCommentRepository

    @Binds
    abstract fun bindNotificationRepository(
        notificationRepositoryImpl: NotificationRepositoryImpl,
    ): NotificationRepository
}
