package com.onewx2m.remote.model.response.category

import kotlinx.serialization.Serializable

@Serializable
data class CongestionHistoricalDateCategoryResponse(
    val key: String,
    val value: String,
    val query: String,
)
