package com.onewx2m.remote.model.response.category

import com.onewx2m.data.model.category.CongestionLevelCategoryEntity
import kotlinx.serialization.Serializable

@Serializable
data class CongestionLevelCategoryResponse(
    val key: String,
    val value: String,
)

fun CongestionLevelCategoryResponse.toEntity() = CongestionLevelCategoryEntity(
    key = key,
    value = value,
)
