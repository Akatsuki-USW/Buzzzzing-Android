package com.onewx2m.local.datasource

import com.onewx2m.data.datasource.LocalCategoryDataSource
import com.onewx2m.data.model.category.CongestionHistoricalDateCategoryEntity
import com.onewx2m.data.model.category.CongestionLevelCategoryEntity
import com.onewx2m.data.model.category.EntireCategoryEntity
import com.onewx2m.data.model.category.LocationCategoryEntity
import com.onewx2m.data.model.category.SpotCategoryEntity
import com.onewx2m.local.dao.CategoryDao
import com.onewx2m.local.model.toEntity
import com.onewx2m.local.model.toPref
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class LocalCategoryDataSourceImpl @Inject constructor(
    private val dao: CategoryDao,
) : LocalCategoryDataSource {
    override fun getLocationCategoryList(): Flow<List<LocationCategoryEntity>> = flow {
        emit(dao.getLocationCategoryList().map { it.toEntity() })
    }

    override fun getSpotCategoryList(): Flow<List<SpotCategoryEntity>> = flow {
        emit(dao.getSpotCategoryList().map { it.toEntity() })
    }

    override fun getCongestionLevelCategoryList(): Flow<List<CongestionLevelCategoryEntity>> =
        flow {
            emit(dao.getCongestionLevelCategoryList().map { it.toEntity() })
        }

    override fun getCongestionHistoricalDateCategoryList(): Flow<List<CongestionHistoricalDateCategoryEntity>> =
        flow {
            emit(dao.getCongestionHistoricalDateCategoryList().map { it.toEntity() })
        }

    override suspend fun saveEntireCategory(entireCategoryEntity: EntireCategoryEntity) {
        val entireCategoryPref = entireCategoryEntity.toPref()
        dao.insertOrUpdateLocationCategory(entireCategoryPref.locationCategoryList)
        dao.insertOrUpdateSpotCategory(entireCategoryPref.spotCategoryList)
        dao.insertOrUpdateCongestionLevelCategory(entireCategoryPref.congestionLevelCategoryList)
        dao.insertOrUpdateCongestionHistoricalDateCategory(entireCategoryPref.congestionHistoricalDateList)
    }
}
