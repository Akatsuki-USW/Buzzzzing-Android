package com.onewx2m.remote.model.response.category

import kotlinx.serialization.Serializable

@Serializable
data class EntireCategoryResponse(
    val locationCategoryList: List<LocationCategoryResponse>,
    val spotCategoryList: List<SpotCategoryResponse>,
    val congestionLevelCategoryList: List<CongestionLevelCategoryResponse>,
    val congestionHistoricalDateList: List<CongestionHistoricalDateCategoryResponse>,
)
