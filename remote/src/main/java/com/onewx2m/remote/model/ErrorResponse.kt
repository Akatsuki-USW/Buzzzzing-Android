package com.onewx2m.remote.model

import kotlinx.serialization.Serializable

@Serializable
data class ErrorResponse(
    val message: String = "",
    val statusCode: Int = -1,
)