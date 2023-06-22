package com.onewx2m.remote.model

import kotlinx.serialization.Serializable

@Serializable
data class ApiResponse<D>(
    val data : D? = null,
    val message: String = "",
    val statusCode: Int = -1,
)