package com.onewx2m.remote.model.response

import com.onewx2m.data.model.UserInfoEntity
import kotlinx.serialization.Serializable

@Serializable
data class UserInfoResponse(
    val email: String = "",
    val nickname: String = "",
    val profileImageUrl: String = ""
)

fun UserInfoResponse.toEntity() = UserInfoEntity(
    email = email,
    nickname = nickname,
    profileImageUrl = profileImageUrl
)
