package com.onewx2m.remote.model.response

import com.onewx2m.data.model.BuzzzzingLocationDetailInfoEntity
import kotlinx.serialization.Serializable

@Serializable
data class BuzzzzingLocationDetailInfoResponse(
    val id: Int,
    val name: String,
    val categoryId: Int,
    val categoryName: String,
    val isBookmarked: Boolean,
    val congestionSymbol: String,
    val congestionLevel: Int,
    val congestionId: Int,
    val observedAt: String,
    val mayRelaxAt: String? = null,
    val mayRelaxUntil: String? = null,
    val mayBuzzAt: Int? = null,
    val mayBuzzUntil: String? = null,
)

fun BuzzzzingLocationDetailInfoResponse.toEntity() = BuzzzzingLocationDetailInfoEntity(
    id = id,
    name = name,
    categoryId = categoryId,
    categoryName = categoryName,
    isBookmarked = isBookmarked,
    congestionSymbol = congestionSymbol,
    congestionLevel = congestionLevel,
    congestionId = congestionId,
    observedAt = observedAt,
    mayRelaxAt = mayRelaxAt,
    mayRelaxUntil = mayRelaxUntil,
    mayBuzzAt = mayBuzzAt,
    mayBuzzUntil = mayBuzzUntil,
)
