package com.onewx2m.remote.model.response.category

import com.onewx2m.data.model.category.LocationCategoryEntity
import kotlinx.serialization.Serializable

@Serializable
data class LocationCategoryResponse(
    val id: Int,
    val name: String,
    val iconImageUrl: String = "",
)

fun LocationCategoryResponse.toEntity() = LocationCategoryEntity(
    id = id,
    name = name,
    iconImageUrl = iconImageUrl,
)
