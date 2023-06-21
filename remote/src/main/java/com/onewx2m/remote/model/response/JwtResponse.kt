package com.onewx2m.remote.model.response

import com.onewx2m.data.model.JwtEntity

data class JwtResponse(
    val accessToken: String,
    val refreshToken: String
)

fun JwtResponse.toEntity(): JwtEntity =
    JwtEntity(
        accessToken = accessToken,
        refreshToken = refreshToken
    )