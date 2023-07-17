package com.onewx2m.data.model

import com.onewx2m.domain.model.BuzzzzingLocationBookmark

data class BuzzzzingLocationBookmarkEntity(
    val locationId: Int,
    val isBookmarked: Boolean,
)

fun BuzzzzingLocationBookmarkEntity.toDomain() = BuzzzzingLocationBookmark(
    locationId = locationId,
    isBookmarked = isBookmarked,
)
