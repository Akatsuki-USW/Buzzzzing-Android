package com.onewx2m.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.Companion.REPLACE
import androidx.room.Query
import com.onewx2m.local.TableName
import com.onewx2m.local.model.CongestionHistoricalDateCategoryPref
import com.onewx2m.local.model.CongestionLevelCategoryPref
import com.onewx2m.local.model.LocationCategoryPref
import com.onewx2m.local.model.SpotCategoryPref

@Dao
interface CategoryDao {
    @Insert(onConflict = REPLACE)
    suspend fun insertOrUpdateCongestionHistoricalDateCategory(vararg data: List<CongestionHistoricalDateCategoryPref>)

    @Insert(onConflict = REPLACE)
    suspend fun insertOrUpdateCongestionLevelCategory(vararg data: List<CongestionLevelCategoryPref>)

    @Insert(onConflict = REPLACE)
    suspend fun insertOrUpdateLocationCategory(data: List<LocationCategoryPref>)

    @Insert(onConflict = REPLACE)
    suspend fun insertOrUpdateSpotCategory(vararg data: List<SpotCategoryPref>)

    @Query("SELECT * FROM ${TableName.CONGESTION_HISTORICAL_DATE_CATEGORY}")
    fun getCongestionHistoricalDateCategoryList(): List<CongestionHistoricalDateCategoryPref>

    @Query("SELECT * FROM ${TableName.CONGESTION_LEVEL_CATEGORY}")
    fun getCongestionLevelCategoryList(): List<CongestionLevelCategoryPref>

    @Query("SELECT * FROM ${TableName.LOCATION_CATEGORY}")
    fun getLocationCategoryList(): List<LocationCategoryPref>

    @Query("SELECT * FROM ${TableName.SPOT_CATEGORY}")
    fun getSpotCategoryList(): List<SpotCategoryPref>
}
