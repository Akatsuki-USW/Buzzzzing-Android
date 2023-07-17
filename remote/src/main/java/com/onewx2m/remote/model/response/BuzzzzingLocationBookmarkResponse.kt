package com.onewx2m.remote.model.response

import com.onewx2m.data.model.BuzzzzingLocationBookmarkEntity
import kotlinx.serialization.Serializable

@Serializable
data class BuzzzzingLocationBookmarkResponse(
    val locationId: Int,
    val isBookmarked: Boolean,
)

fun BuzzzzingLocationBookmarkResponse.toEntity() = BuzzzzingLocationBookmarkEntity(
    locationId = locationId,
    isBookmarked = isBookmarked,
)
