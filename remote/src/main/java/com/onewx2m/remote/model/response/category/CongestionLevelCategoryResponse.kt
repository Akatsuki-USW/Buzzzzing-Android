package com.onewx2m.remote.model.response.category

import kotlinx.serialization.Serializable

@Serializable
data class CongestionLevelCategoryResponse(
    val key: String,
    val value: String,
)
