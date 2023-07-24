package com.onewx2m.data.model

import com.onewx2m.domain.model.SpotBookmark

data class SpotBookmarkEntity(
    val spotId: Int,
    val isBookmarked: Boolean,
)

fun SpotBookmarkEntity.toDomain() = SpotBookmark(
    spotId = spotId,
    isBookmarked = isBookmarked,
)
