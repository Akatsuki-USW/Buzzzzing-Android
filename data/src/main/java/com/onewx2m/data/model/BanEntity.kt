package com.onewx2m.data.model

import com.onewx2m.domain.model.Ban

data class BanEntity(
    val title: String,
    val content: String,
    val banStartAt: String,
    val banEndedAt: String,
)

fun BanEntity.toDomain() = Ban(
    title = title,
    content = content,
    banStartAt = banStartAt,
    banEndedAt = banEndedAt,
)
