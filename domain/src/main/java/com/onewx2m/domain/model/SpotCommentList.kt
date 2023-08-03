package com.onewx2m.domain.model

data class SpotCommentList(
    val totalElements: Int,
    val last: Boolean,
    val content: List<SpotComment>,
)
