package com.onewx2m.remote.model.response.category

import com.onewx2m.data.model.category.SpotCategoryEntity
import kotlinx.serialization.Serializable

@Serializable
data class SpotCategoryResponse(
    val id: Int,
    val name: String,
)

fun SpotCategoryResponse.toEntity() = SpotCategoryEntity(
    id = id,
    name = name,
)
