package com.onewx2m.data.model

import com.onewx2m.domain.model.Jwt
import com.onewx2m.domain.model.UserInfo

data class UserInfoEntity(
    val email: String,
    val nickname: String,
    val profileImageUrl: String,
)

fun UserInfoEntity.toDomain(): UserInfo = UserInfo(
    email = email,
    nickname = nickname,
    profileImageUrl = profileImageUrl
)
