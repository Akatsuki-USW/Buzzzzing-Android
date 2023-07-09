package com.onewx2m.data.model.category

import com.onewx2m.domain.model.category.CongestionLevelCategory

data class CongestionLevelCategoryEntity(
    val key: String,
    val value: String,
)

fun CongestionLevelCategoryEntity.toDomain() = CongestionLevelCategory(
    key = key,
    value = value,
)
