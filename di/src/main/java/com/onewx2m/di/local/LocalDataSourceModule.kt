@file:Suppress("UnnecessaryAbstractClass")

package com.onewx2m.di.local

import com.onewx2m.data.datasource.LocalAuthDataSource
import com.onewx2m.data.datasource.RemoteAuthDataSource
import com.onewx2m.local.datasource.LocalAuthDataSourceImpl
import com.onewx2m.remote.datasource.RemoteAuthDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class LocalDataSourceModule {

    @Singleton
    @Binds
    abstract fun provideLocalAuthDataSource(
        localAuthDataSourceImpl: LocalAuthDataSourceImpl
    ): LocalAuthDataSource
}
