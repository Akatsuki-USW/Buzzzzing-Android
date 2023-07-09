package com.onewx2m.domain.model.category

data class EntireCategory(
    val locationCategoryList: List<LocationCategory>,
    val spotCategoryList: List<SpotCategory>,
    val congestionLevelCategoryList: List<CongestionLevelCategory>,
    val congestionHistoricalDateList: List<CongestionHistoricalDateCategory>,
)
