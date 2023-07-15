package com.onewx2m.domain.model

data class BuzzzzingLocation(
    val totalElements: Int,
    val last: Boolean,
    val content: List<BuzzzzingContent>,
)

data class BuzzzzingContent(
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
