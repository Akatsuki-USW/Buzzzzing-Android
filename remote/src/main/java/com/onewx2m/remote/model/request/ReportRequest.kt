package com.onewx2m.remote.model.request

import kotlinx.serialization.Serializable

@Serializable
data class ReportRequest(
    val reportTarget: String,
    val reportTargetId: Int,
    val reportedUserId: Int,
    val content: String,
)
