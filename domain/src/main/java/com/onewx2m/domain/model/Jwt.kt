package com.onewx2m.domain.model

data class Jwt(
    val accessToken: String,
    val refreshToken: String
)
