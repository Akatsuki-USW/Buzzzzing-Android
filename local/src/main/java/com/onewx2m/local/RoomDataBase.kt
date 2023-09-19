package com.onewx2m.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.onewx2m.local.dao.CategoryDao
import com.onewx2m.local.model.CongestionHistoricalDateCategoryPref
import com.onewx2m.local.model.CongestionLevelCategoryPref
import com.onewx2m.local.model.LocationCategoryPref
import com.onewx2m.local.model.SpotCategoryPref

@Database(
    entities = [
        CongestionLevelCategoryPref::class,
        CongestionHistoricalDateCategoryPref::class,
        LocationCategoryPref::class,
        SpotCategoryPref::class,
    ],
    version = 1,
    exportSchema = false,
)
abstract class RoomDataBase : RoomDatabase() {
    abstract fun categoryDao(): CategoryDao
}
