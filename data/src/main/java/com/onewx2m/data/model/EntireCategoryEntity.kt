package com.onewx2m.data.model

data class EntireCategoryEntity(
    val locationCategoryList: List<LocationCategoryEntity>,
    val spotCategoryList: List<SpotCategoryEntity>,
    val congestionLevelCategoryList: List<CongestionLevelCategoryEntity>,
    val congestionHistoricalDateList: List<CongestionHistoricalDateCategoryEntity>,
)
