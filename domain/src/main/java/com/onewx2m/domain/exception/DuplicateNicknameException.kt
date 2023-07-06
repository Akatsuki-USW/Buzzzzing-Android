package com.onewx2m.domain.exception

class DuplicateNicknameException(
    override val message: String = "중복된 닉네임입니다."
) : RuntimeException()
