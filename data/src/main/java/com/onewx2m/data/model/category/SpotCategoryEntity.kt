package com.onewx2m.data.model.category

import com.onewx2m.domain.model.category.SpotCategory

data class SpotCategoryEntity(
    val id: Int,
    val name: String,
)

fun SpotCategoryEntity.toDomain() = SpotCategory(
    id = id,
    name = name,
)
