package com.onewx2m.data.model

import com.onewx2m.domain.model.BuzzzzingStatistics
import com.onewx2m.domain.model.BuzzzzingStatisticsContent

data class BuzzzzingStatisticsEntity(
    val id: Int,
    val content: List<BuzzzzingStatisticsContentEntity>,
)

data class BuzzzzingStatisticsContentEntity(
    val time: Int,
    val congestionLevel: Int,
)

fun BuzzzzingStatisticsEntity.toDomain() = BuzzzzingStatistics(
    id = id,
    content = content.map { it.toDomain() }
)

fun BuzzzzingStatisticsContentEntity.toDomain() = BuzzzzingStatisticsContent(
    time = time,
    congestionLevel = congestionLevel,
)