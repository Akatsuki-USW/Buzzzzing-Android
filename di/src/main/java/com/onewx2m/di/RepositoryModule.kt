@file:Suppress("UnnecessaryAbstractClass")

package com.onewx2m.di

import com.onewx2m.data.repository.AuthRepositoryImpl
import com.onewx2m.data.repository.CategoryRepositoryImpl
import com.onewx2m.data.repository.OtherRepositoryImpl
import com.onewx2m.data.repository.UserRepositoryImpl
import com.onewx2m.domain.repository.AuthRepository
import com.onewx2m.domain.repository.CategoryRepository
import com.onewx2m.domain.repository.OtherRepository
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
}
