package com.onewx2m.data.repository

import com.onewx2m.data.datasource.LocalCategoryDataSource
import com.onewx2m.data.datasource.RemoteOtherDataSource
import com.onewx2m.data.extension.flatMapDomain
import com.onewx2m.data.extension.flatMapOutcomeSuccess
import com.onewx2m.data.model.category.toDomain
import com.onewx2m.domain.Outcome
import com.onewx2m.domain.model.category.CongestionHistoricalDateCategory
import com.onewx2m.domain.model.category.CongestionLevelCategory
import com.onewx2m.domain.model.category.LocationCategory
import com.onewx2m.domain.model.category.SpotCategory
import com.onewx2m.domain.repository.CategoryRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class CategoryRepositoryImpl @Inject constructor(
    private val remoteOtherDataSource: RemoteOtherDataSource,
    private val localCategoryDataSource: LocalCategoryDataSource,
) : CategoryRepository {

    override fun getLocationCategoryList(): Flow<List<LocationCategory>> {
        return localCategoryDataSource.getLocationCategoryList().flatMapDomain { data ->
            data.map { it.toDomain() }
        }
    }

    override fun getSpotCategoryList(): Flow<List<SpotCategory>> {
        return localCategoryDataSource.getSpotCategoryList().flatMapDomain { data ->
            data.map { it.toDomain() }
        }
    }

    override fun getCongestionLevelCategoryList(): Flow<List<CongestionLevelCategory>> {
        return localCategoryDataSource.getCongestionLevelCategoryList().flatMapDomain { data ->
            data.map { it.toDomain() }
        }
    }

    override fun getCongestionHistoricalDateCategoryList(): Flow<List<CongestionHistoricalDateCategory>> {
        return localCategoryDataSource.getCongestionHistoricalDateCategoryList()
            .flatMapDomain { data ->
                data.map { it.toDomain() }
            }
    }

    override suspend fun saveEntireCategory(): Flow<Outcome<Unit>> =
        remoteOtherDataSource.getEntireCategory().flatMapOutcomeSuccess {
            localCategoryDataSource.saveEntireCategory(it)
        }
}
