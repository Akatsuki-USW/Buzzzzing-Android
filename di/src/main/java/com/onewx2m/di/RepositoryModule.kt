@file:Suppress("UnnecessaryAbstractClass")

package com.onewx2m.di

import com.onewx2m.data.datasource.RemoteAuthDataSource
import com.onewx2m.data.repository.AuthRepositoryImpl
import com.onewx2m.domain.repository.AuthRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Singleton
    @Binds
    abstract fun bindAuthRepository(
        authRepositoryImpl: AuthRepositoryImpl
    ): AuthRepository
}
