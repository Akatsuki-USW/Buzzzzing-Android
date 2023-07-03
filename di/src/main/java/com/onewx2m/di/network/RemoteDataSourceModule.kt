@file:Suppress("UnnecessaryAbstractClass")

package com.onewx2m.di.network

import com.onewx2m.data.datasource.RemoteAuthDataSource
import com.onewx2m.data.datasource.RemoteOtherDataSource
import com.onewx2m.remote.datasource.RemoteAuthDataSourceImpl
import com.onewx2m.remote.datasource.RemoteOtherDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RemoteDataSourceModule {

    @Binds
    abstract fun bindRemoteAuthDatasource(
        remoteAuthDataSourceImpl: RemoteAuthDataSourceImpl,
    ): RemoteAuthDataSource

    @Binds
    abstract fun bindRemoteOtherDatasource(
        remoteOtherDataSourceImpl: RemoteOtherDataSourceImpl,
    ): RemoteOtherDataSource
}
