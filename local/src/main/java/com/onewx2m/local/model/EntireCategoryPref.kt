package com.onewx2m.local.model

import com.onewx2m.data.model.EntireCategoryEntity

data class EntireCategoryPref(
    val locationCategoryList: List<LocationCategoryPref>,
    val spotCategoryList: List<SpotCategoryPref>,
    val congestionLevelCategoryList: List<CongestionLevelCategoryPref>,
    val congestionHistoricalDateList: List<CongestionHistoricalDateCategoryPref>,
)

fun EntireCategoryEntity.toPref() = EntireCategoryPref(
    locationCategoryList = locationCategoryList.map { it.toPref() },
    spotCategoryList = spotCategoryList.map { it.toPref() },
    congestionHistoricalDateList = congestionHistoricalDateList.map { it.toPref() },
    congestionLevelCategoryList = congestionLevelCategoryList.map { it.toPref() },
)
