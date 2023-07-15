package com.onewx2m.data.model

import com.onewx2m.domain.model.BuzzzzingContent
import com.onewx2m.domain.model.BuzzzzingLocation

data class BuzzzzingLocationEntity(
    val totalElements: Int,
    val last: Boolean,
    val content: List<BuzzzzingContentEntity>,
)

data class BuzzzzingContentEntity(
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

fun BuzzzzingLocationEntity.toDomain() = BuzzzzingLocation(
    totalElements = totalElements,
    last = last,
    content = content.map { it.toDomain() }
)

fun BuzzzzingContentEntity.toDomain() = BuzzzzingContent(
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
