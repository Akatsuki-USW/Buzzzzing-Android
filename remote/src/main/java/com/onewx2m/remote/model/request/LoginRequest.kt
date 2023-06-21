package com.onewx2m.remote.model.request

import kotlinx.serialization.Serializable

@Serializable
data class LoginRequest(
    val oauthAccessToken: String,
    val socialType: String
)
