package com.onewx2m.remote.model.response.category

import com.onewx2m.data.model.category.EntireCategoryEntity
import kotlinx.serialization.Serializable

@Serializable
data class EntireCategoryResponse(
    val locationCategories: List<LocationCategoryResponse>,
    val spotCategories: List<SpotCategoryResponse>,
    val congestionLevelChoices: List<CongestionLevelCategoryResponse>,
    val congestionHistoricalDateChoices: List<CongestionHistoricalDateCategoryResponse>,
)

fun EntireCategoryResponse.toEntity() = EntireCategoryEntity(
    locationCategoryList = locationCategories.map { it.toEntity() },
    spotCategoryList = spotCategories.map { it.toEntity() },
    congestionLevelCategoryList = congestionLevelChoices.map { it.toEntity() },
    congestionHistoricalDateList = congestionHistoricalDateChoices.map { it.toEntity() },
)
