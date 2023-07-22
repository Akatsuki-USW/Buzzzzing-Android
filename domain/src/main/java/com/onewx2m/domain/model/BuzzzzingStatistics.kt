package com.onewx2m.domain.model

data class BuzzzzingStatistics(
    val id: Int,
    val content: List<BuzzzzingStatisticsContent>,
)

data class BuzzzzingStatisticsContent(
    val time: Int,
    val congestionLevel: Int,
)
