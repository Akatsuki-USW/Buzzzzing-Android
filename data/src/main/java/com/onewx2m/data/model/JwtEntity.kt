package com.onewx2m.data.model

import com.onewx2m.domain.model.Jwt

data class JwtEntity(
    val accessToken: String,
    val refreshToken: String
)

fun JwtEntity.toDomain(): Jwt = Jwt(
    accessToken = accessToken,
    refreshToken = refreshToken
)
