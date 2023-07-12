package com.onewx2m.domain.usecase

import com.onewx2m.domain.repository.UserRepository
import java.io.File
import javax.inject.Inject

class EditMyInfoUseCase @Inject constructor(
    private val userRepository: UserRepository,
) {
    suspend operator fun invoke(
        nickname: String,
        email: String,
        profileImageUrl: String,
        profileFile: File?,
    ) = userRepository.editMyInfo(
        nickname = nickname,
        email = email,
        profileImageUrl = profileImageUrl,
        profileFile = profileFile,
    )
}
