package com.onewx2m.remote.model.response

import com.onewx2m.data.model.BanEntity
import kotlinx.serialization.Serializable

@Serializable
data class BanListResponse(
    val banList: List<BanResponse>,
)

@Serializable
data class BanResponse(
    val title: String,
    val content: String,
    val banStartAt: String,
    val banEndedAt: String,
)

internal fun BanListResponse.toEntity() = banList.map { it.toEntity() }

internal fun BanResponse.toEntity() = BanEntity(
    title = title,
    content = content,
    banStartAt = banStartAt,
    banEndedAt = banEndedAt,
)
