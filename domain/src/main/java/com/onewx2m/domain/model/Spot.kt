package com.onewx2m.domain.model

data class SpotList(
    val totalElements: Int,
    val last: Boolean,
    val content: List<Spot>,
)

data class Spot(
    val id: Int,
    val title: String,
    val address: String,
    val thumbnailImageUrl: String? = null,
    val isBookmarked: Boolean,
    val userId: Int,
    val userNickname: String,
    val userProfileImageUrl: String,
)
