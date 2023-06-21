package com.onewx2m.remote.model.response

import com.onewx2m.data.model.BuzzzzingJwtEntity

data class JwtResponse(
    val accessToken: String,
    val refreshToken: String
)

fun JwtResponse.toEntity(): BuzzzzingJwtEntity =
    BuzzzzingJwtEntity(
        accessToken = accessToken,
        refreshToken = refreshToken
    )