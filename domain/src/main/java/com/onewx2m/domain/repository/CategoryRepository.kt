package com.onewx2m.domain.repository

import com.onewx2m.domain.Outcome
import com.onewx2m.domain.model.category.CongestionHistoricalDateCategory
import com.onewx2m.domain.model.category.CongestionLevelCategory
import com.onewx2m.domain.model.category.LocationCategory
import com.onewx2m.domain.model.category.SpotCategory
import kotlinx.coroutines.flow.Flow

interface CategoryRepository {
    fun getLocationCategoryList(): Flow<List<LocationCategory>>
    fun getSpotCategoryList(): Flow<List<SpotCategory>>
    fun getCongestionLevelCategoryList(): Flow<List<CongestionLevelCategory>>
    fun getCongestionHistoricalDateCategoryList(): Flow<List<CongestionHistoricalDateCategory>>

    suspend fun saveEntireCategory(): Flow<Outcome<Unit>>
}
