package com.onewx2m.remote.model.response.category

import com.onewx2m.data.model.category.CongestionHistoricalDateCategoryEntity
import kotlinx.serialization.Serializable

@Serializable
data class CongestionHistoricalDateCategoryResponse(
    val key: String,
    val value: String,
    val query: String,
)

fun CongestionHistoricalDateCategoryResponse.toEntity() = CongestionHistoricalDateCategoryEntity(
    key = key,
    value = value,
    query = query,
)
