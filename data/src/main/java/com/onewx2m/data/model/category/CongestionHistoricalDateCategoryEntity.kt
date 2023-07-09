package com.onewx2m.data.model.category

import com.onewx2m.domain.model.category.CongestionHistoricalDateCategory

data class CongestionHistoricalDateCategoryEntity(
    val key: String,
    val value: String,
    val query: String,
)

fun CongestionHistoricalDateCategoryEntity.toDomain() = CongestionHistoricalDateCategory(
    key = key,
    value = value,
    query = query,
)
