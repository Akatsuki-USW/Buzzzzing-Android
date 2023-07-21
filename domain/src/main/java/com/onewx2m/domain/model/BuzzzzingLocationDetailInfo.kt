package com.onewx2m.domain.model

data class BuzzzzingLocationDetailInfo(
    val id: Int,
    val name: String,
    val categoryId: Int,
    val categoryName: String,
    val isBookmarked: Boolean,
    val bookMarkCount: Int,
    val congestionSymbol: String,
    val congestionLevel: Int,
    val congestionId: Int,
    val observedAt: String,
    val mayRelaxAt: Int? = null,
    val mayRelaxUntil: Int? = null,
    val mayBuzzAt: Int? = null,
    val mayBuzzUntil: Int? = null,
)
