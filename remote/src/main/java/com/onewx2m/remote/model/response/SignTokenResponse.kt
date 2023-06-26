package com.onewx2m.remote.model.response

import kotlinx.serialization.Serializable

@Serializable
data class SignTokenResponse(
    val signToken: String
)