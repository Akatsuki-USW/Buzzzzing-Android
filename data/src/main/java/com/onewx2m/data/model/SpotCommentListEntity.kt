package com.onewx2m.data.model

import com.onewx2m.domain.model.SpotCommentList

data class SpotCommentListEntity(
    val totalElements: Int,
    val last: Boolean,
    val content: List<SpotCommentEntity>,
)

internal fun SpotCommentListEntity.toDomain() = SpotCommentList(
    totalElements = totalElements,
    last = last,
    content = content.map { it.toDomain() },
)
