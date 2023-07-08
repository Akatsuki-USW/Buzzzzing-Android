package com.onewx2m.remote.model.response.category

import kotlinx.serialization.Serializable

@Serializable
data class LocationCategoryResponse(
    val id: Int,
    val name: String,
    val iconImageUrl: String,
)
