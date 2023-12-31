package com.onewx2m.data.model

import com.onewx2m.domain.model.SpotComment

data class SpotCommentEntity(
    val childCount: Int = 0,
    val content: String = "",
    val createdAt: String = "",
    val id: Int = -1,
    val isAuthor: Boolean = false,
    val parentId: Int? = null,
    val presence: Boolean = false,
    val updatedAt: String = "",
    val userId: Int = -1,
    val userNickname: String = "",
    val userProfileImageUrl: String = "",
)

internal fun SpotCommentEntity.toDomain() = SpotComment(
    childCount = childCount,
    content = content,
    createdAt = createdAt,
    id = id,
    isAuthor = isAuthor,
    parentId = parentId,
    presence = presence,
    updatedAt = updatedAt,
    userId = userId,
    userNickname = userNickname,
    userProfileImageUrl = userProfileImageUrl,
)
