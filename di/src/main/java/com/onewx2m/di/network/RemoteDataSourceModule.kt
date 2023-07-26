@file:Suppress("UnnecessaryAbstractClass")

package com.onewx2m.di.network

import com.onewx2m.data.datasource.RemoteAuthDataSource
import com.onewx2m.data.datasource.RemoteBuzzzzingLocationDataSource
import com.onewx2m.data.datasource.RemoteKakaoLocationDataSource
import com.onewx2m.data.datasource.RemoteMediaDataSource
import com.onewx2m.data.datasource.RemoteOtherDataSource
import com.onewx2m.data.datasource.RemoteSpotDataSource
import com.onewx2m.data.datasource.RemoteUserDataSource
import com.onewx2m.remote.datasource.RemoteAuthDataSourceImpl
import com.onewx2m.remote.datasource.RemoteBuzzzzingLocationDataSourceImpl
import com.onewx2m.remote.datasource.RemoteKakaoLocationDataSourceImpl
import com.onewx2m.remote.datasource.RemoteMediaDataSourceImpl
import com.onewx2m.remote.datasource.RemoteOtherDataSourceImpl
import com.onewx2m.remote.datasource.RemoteSpotDataSourceImpl
import com.onewx2m.remote.datasource.RemoteUserDataSourceImpl
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

    @Binds
    abstract fun bindRemoteUserDatasource(
        remoteUserDataSourceImpl: RemoteUserDataSourceImpl,
    ): RemoteUserDataSource

    @Binds
    abstract fun bindRemoteMediaDatasource(
        remoteMediaDataSourceImpl: RemoteMediaDataSourceImpl,
    ): RemoteMediaDataSource

    @Binds
    abstract fun bindRemoteBuzzzzingLocationDatasource(
        remoteBuzzzingLocationDataSourceImpl: RemoteBuzzzzingLocationDataSourceImpl,
    ): RemoteBuzzzzingLocationDataSource

    @Binds
    abstract fun bindRemoteSpotDatasource(
        remoteSpotDataSourceImpl: RemoteSpotDataSourceImpl,
    ): RemoteSpotDataSource

    @Binds
    abstract fun bindRemoteKakaoDatasource(
        remoteKakaoLocationDataSourceImpl: RemoteKakaoLocationDataSourceImpl,
    ): RemoteKakaoLocationDataSource
}
