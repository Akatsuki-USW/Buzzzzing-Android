package com.onewx2m.remote.model.response

import com.onewx2m.data.model.BuzzzzingContentEntity
import com.onewx2m.data.model.BuzzzzingLocationEntity
import kotlinx.serialization.Serializable

@Serializable
data class BuzzzzingLocationResponse(
    val totalElements: Int,
    val last: Boolean,
    val content: List<BuzzzzingContentResponse>,
)

@Serializable
data class BuzzzzingContentResponse(
    val id: Int,
    val name: String,
    val categoryId: Int,
    val categoryName: String,
    val categoryIconUrl: String,
    val isBookmarked: Boolean,
    val congestionSymbol: String,
    val congestionLevel: Int,
    val bookMarkCount: Int,
)

fun BuzzzzingLocationResponse.toEntity() = BuzzzzingLocationEntity(
    totalElements = totalElements,
    last = last,
    content = content.map { it.toEntity() }
)

fun BuzzzzingContentResponse.toEntity() = BuzzzzingContentEntity(
    id = id,
    name = name,
    categoryId = categoryId,
    categoryName = categoryName,
    categoryIconUrl = categoryIconUrl,
    isBookmarked = isBookmarked,
    congestionSymbol = congestionSymbol,
    congestionLevel = congestionLevel,
    bookMarkCount = bookMarkCount,
)