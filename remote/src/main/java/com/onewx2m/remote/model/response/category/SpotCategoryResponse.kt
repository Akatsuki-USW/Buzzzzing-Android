package com.onewx2m.remote.model.response.category

import kotlinx.serialization.Serializable

@Serializable
data class SpotCategoryResponse(
    val id: Int,
    val name: String,
)
