package com.onewx2m.remote.model.response

import com.onewx2m.data.model.SpotDetailEntity
import com.onewx2m.remote.util.UNKNOWN_USER
import kotlinx.serialization.Serializable

@Serializable
data class SpotDetailResponse(
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
    val userNickname: String = UNKNOWN_USER,
    val userProfileImageUrl: String? = "",
    val isBookmarked: Boolean = false,
    val isAuthor: Boolean = false,
)

fun SpotDetailResponse.toEntity() = SpotDetailEntity(
    id = id,
    title = title,
    address = address,
    content = content,
    imageUrls = imageUrls,
    createdAt = createdAt,
    updatedAt = updatedAt,
    locationId = locationId,
    locationName = locationName,
    spotCategoryId = spotCategoryId,
    spotCategoryName = spotCategoryName,
    userId = userId,
    userNickname = userNickname,
    userProfileImageUrl = userProfileImageUrl,
    isBookmarked = isBookmarked,
    isAuthor = isAuthor,
)
