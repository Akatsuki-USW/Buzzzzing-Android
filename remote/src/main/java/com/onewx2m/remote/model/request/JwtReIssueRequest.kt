package com.onewx2m.remote.model.request

import kotlinx.serialization.Serializable

@Serializable
data class JwtReIssueRequest(
    val refreshToken: String
)
