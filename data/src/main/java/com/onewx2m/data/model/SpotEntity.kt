package com.onewx2m.data.model

import com.onewx2m.domain.model.Spot
import com.onewx2m.domain.model.SpotList

data class SpotListEntity(
    val totalElements: Int,
    val last: Boolean,
    val content: List<SpotEntity>,
)

data class SpotEntity(
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

fun SpotListEntity.toDomain() = SpotList(
    totalElements = totalElements,
    last = last,
    content = content.map { it.toDomain() },
)

fun SpotEntity.toDomain() = Spot(
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
