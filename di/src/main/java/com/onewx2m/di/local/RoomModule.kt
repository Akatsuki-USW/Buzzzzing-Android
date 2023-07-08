package com.onewx2m.di.local

import android.content.Context
import androidx.room.Room
import com.onewx2m.di.RoomDB
import com.onewx2m.local.RoomDataBase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RoomModule {

    private const val DB_CATEGORY = "database-category"

    @Provides
    @Singleton
    @RoomDB
    fun provideRoomDataBase(
        @ApplicationContext context: Context,
    ) = Room.databaseBuilder(
        context,
        RoomDataBase::class.java,
        DB_CATEGORY,
    ).build()
}
