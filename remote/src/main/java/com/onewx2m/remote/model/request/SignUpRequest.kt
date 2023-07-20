package com.onewx2m.remote.model.request

import kotlinx.serialization.Serializable

@Serializable
data class SignUpRequest(
    val signToken: String,
    val nickname: String,
    val email: String,
    val profileImageUrl: String,
    val fcmToken: String,
)
