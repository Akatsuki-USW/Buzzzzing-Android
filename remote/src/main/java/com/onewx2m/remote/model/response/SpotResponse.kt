package com.onewx2m.remote.model.response

import com.onewx2m.data.model.SpotEntity
import com.onewx2m.data.model.SpotListEntity
import kotlinx.serialization.Serializable

@Serializable
data class SpotListResponse(
    val totalElements: Int,
    val last: Boolean,
    val content: List<SpotResponse>,
)

@Serializable
data class SpotResponse(
    val id: Int,
    val title: String,
    val address: String,
    val thumbnailImageUrl: String,
    val isBookmarked: String,
    val spotCategoryId: Int,
    val userId: Int,
    val userNickname: String,
    val userProfileImageUrl: String,
)

fun SpotListResponse.toEntity() = SpotListEntity(
    totalElements = totalElements,
    last = last,
    content = content.map { it.toEntity() },
)

fun SpotResponse.toEntity() = SpotEntity(
    id = id,
    title = title,
    address = address,
    thumbnailImageUrl = thumbnailImageUrl,
    isBookmarked = isBookmarked,
    spotCategoryId = spotCategoryId,
    userId = userId,
    userNickname = userNickname,
    userProfileImageUrl = userProfileImageUrl,
)
