package com.onewx2m.remote.model.response

import com.onewx2m.data.model.SpotCommentListEntity
import kotlinx.serialization.Serializable

@Serializable
data class SpotCommentListResponse(
    val totalElements: Int,
    val last: Boolean,
    val content: List<SpotCommentResponse>,
)

internal fun SpotCommentListResponse.toEntity() = SpotCommentListEntity(
    totalElements = totalElements,
    last = last,
    content = content.map { it.toEntity() },
)
