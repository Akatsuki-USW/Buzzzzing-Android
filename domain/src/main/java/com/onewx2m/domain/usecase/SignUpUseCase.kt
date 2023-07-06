package com.onewx2m.domain.usecase

import com.onewx2m.domain.repository.OtherRepository
import java.io.File
import javax.inject.Inject

class SignUpUseCase @Inject constructor(
    private val otherRepository: OtherRepository,
) {
    suspend operator fun invoke(
        file: File?,
        signToken: String,
        nickname: String,
        email: String,
    ) =
        otherRepository.signUp(
            file = file,
            signToken = signToken,
            nickname = nickname,
            email = email,
        )
}
