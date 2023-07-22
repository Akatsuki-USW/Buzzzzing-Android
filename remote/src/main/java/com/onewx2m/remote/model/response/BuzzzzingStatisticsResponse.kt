package com.onewx2m.remote.model.response

import com.onewx2m.data.model.BuzzzzingStatisticsContentEntity
import com.onewx2m.data.model.BuzzzzingStatisticsEntity
import com.onewx2m.domain.model.BuzzzzingStatistics
import com.onewx2m.domain.model.BuzzzzingStatisticsContent
import kotlinx.serialization.Serializable

@Serializable
data class BuzzzzingStatisticsResponse(
    val id: Int,
    val content: List<BuzzzzingStatisticsContentResponse>,
)

@Serializable
data class BuzzzzingStatisticsContentResponse(
    val time: Int,
    val congestionLevel: Int,
)

fun BuzzzzingStatisticsResponse.toEntity() = BuzzzzingStatisticsEntity(
    id = id,
    content = content.map { it.toEntity() }
)

fun BuzzzzingStatisticsContentResponse.toEntity() = BuzzzzingStatisticsContentEntity(
    time = time,
    congestionLevel = congestionLevel,
)