package com.onewx2m.domain.model

data class BuzzzzingLocationDetailInfo(
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
