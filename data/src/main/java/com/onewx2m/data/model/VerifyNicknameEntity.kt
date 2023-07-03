package com.onewx2m.data.model

import com.onewx2m.domain.model.VerifyNickname

data class VerifyNicknameEntity(
    val isAvailable: Boolean,
)

fun VerifyNicknameEntity.toDomain(): VerifyNickname = VerifyNickname(
    isAvailable = isAvailable,
)
