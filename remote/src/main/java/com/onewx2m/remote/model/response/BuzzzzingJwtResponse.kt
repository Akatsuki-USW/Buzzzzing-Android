package com.onewx2m.remote.model.response

import com.onewx2m.data.model.BuzzzzingJwtEntity

data class BuzzzzingJwtResponse(
    val accessToken: String,
    val refreshToken: String
)

fun BuzzzzingJwtResponse.toEntity(): BuzzzzingJwtEntity =
    BuzzzzingJwtEntity(
        accessToken = accessToken,
        refreshToken = refreshToken
    )