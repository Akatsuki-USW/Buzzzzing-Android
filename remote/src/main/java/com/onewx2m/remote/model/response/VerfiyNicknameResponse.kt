package com.onewx2m.remote.model.response

import com.onewx2m.data.model.VerifyNicknameEntity
import kotlinx.serialization.Serializable

@Serializable
data class VerifyNicknameResponse(
    val isAvailableNickname: Boolean,
)

fun VerifyNicknameResponse.toEntity(): VerifyNicknameEntity =
    VerifyNicknameEntity(
        isAvailable = isAvailableNickname,
    )
