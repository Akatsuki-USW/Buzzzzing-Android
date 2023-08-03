package com.onewx2m.domain.model

data class SpotComment(
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
