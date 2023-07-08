package com.onewx2m.di.local

import com.onewx2m.di.RoomDB
import com.onewx2m.local.RoomDataBase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DaoModule {

    @Singleton
    @Provides
    fun provideRecentSearchDao(@RoomDB db: RoomDataBase) = db.categoryDao()
}