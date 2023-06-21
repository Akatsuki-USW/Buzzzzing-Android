@file:Suppress("UnnecessaryAbstractClass")

package com.onewx2m.di.network

import com.onewx2m.data.datasource.RemoteAuthDataSource
import com.onewx2m.remote.datasource.RemoteAuthDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RemoteDataSourceModule {

    @Singleton
    @Binds
    abstract fun provideRemoteAuthDatasource(
        remoteAuthDataSourceImpl: RemoteAuthDataSourceImpl
    ): RemoteAuthDataSource
}
