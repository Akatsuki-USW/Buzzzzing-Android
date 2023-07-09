package com.onewx2m.data.model.category

import com.onewx2m.domain.model.category.LocationCategory

data class LocationCategoryEntity(
    val id: Int,
    val name: String,
    val iconImageUrl: String,
)

fun LocationCategoryEntity.toDomain() = LocationCategory(
    id = id,
    name = name,
    iconImageUrl = iconImageUrl,
)
