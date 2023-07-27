package com.onewx2m.domain.model

data class SpotDetail(
    val id: Int = -1,
    val title: String = "",
    val address: String = "",
    val content: String = "",
    val imageUrls: List<String> = emptyList(),
    val createdAt: String = "",
    val updatedAt: String = "",
    val locationId: Int = -1,
    val locationName: String = "",
    val spotCategoryId: Int = -1,
    val spotCategoryName: String = "",
    val userId: Int = -1,
    val userNickname: String = "",
    val userProfileImageUrl: String? = "",
    val isBookmarked: Boolean = false,
    val isAuthor: Boolean = false,
)
