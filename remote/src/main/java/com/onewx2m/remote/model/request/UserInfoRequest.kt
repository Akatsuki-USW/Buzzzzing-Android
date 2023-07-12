package com.onewx2m.remote.model.request

import kotlinx.serialization.Serializable

@Serializable
data class UserInfoRequest(
    val email: String,
    val nickname: String,
    val profileImageUrl: String,
)
