@file:Suppress("UnnecessaryAbstractClass")

package com.onewx2m.di.local

import com.onewx2m.data.datasource.LocalAuthDataSource
import com.onewx2m.data.datasource.LocalCategoryDataSource
import com.onewx2m.local.datasource.LocalAuthDataSourceImpl
import com.onewx2m.local.datasource.LocalCategoryDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class LocalDataSourceModule {

    @Binds
    abstract fun provideLocalAuthDataSource(
        localAuthDataSourceImpl: LocalAuthDataSourceImpl,
    ): LocalAuthDataSource

    @Binds
    abstract fun provideLocalCategoryDataSource(
        localCategoryDataSourceImpl: LocalCategoryDataSourceImpl,
    ): LocalCategoryDataSource
}
