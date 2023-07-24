package com.onewx2m.remote.model.response

import com.onewx2m.data.model.SpotBookmarkEntity
import kotlinx.serialization.Serializable

@Serializable
data class SpotBookmarkResponse(
    val spotId: Int,
    val isBookmarked: Boolean,
)

fun SpotBookmarkResponse.toEntity() = SpotBookmarkEntity(
    spotId = spotId,
    isBookmarked = isBookmarked,
)
