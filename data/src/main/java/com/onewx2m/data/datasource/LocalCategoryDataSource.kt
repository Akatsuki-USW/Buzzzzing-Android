package com.onewx2m.data.datasource

import com.onewx2m.data.model.CongestionHistoricalDateCategoryEntity
import com.onewx2m.data.model.CongestionLevelCategoryEntity
import com.onewx2m.data.model.EntireCategoryEntity
import com.onewx2m.data.model.LocationCategoryEntity
import com.onewx2m.data.model.SpotCategoryEntity
import kotlinx.coroutines.flow.Flow

interface LocalCategoryDataSource {

    fun getLocationCategoryList(): Flow<List<LocationCategoryEntity>>
    fun getSpotCategoryList(): Flow<List<SpotCategoryEntity>>
    fun getCongestionLevelCategoryList(): Flow<List<CongestionLevelCategoryEntity>>
    fun getCongestionHistoricalDateCategoryList(): Flow<List<CongestionHistoricalDateCategoryEntity>>

    suspend fun saveEntireCategory(entireCategoryEntity: EntireCategoryEntity)
}
