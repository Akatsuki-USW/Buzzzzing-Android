package com.onewx2m.domain.usecase

import com.onewx2m.domain.repository.OtherRepository
import javax.inject.Inject

class VerifyNicknameUseCase @Inject constructor(
    private val otherRepository: OtherRepository,
) {
    suspend operator fun invoke(request: String) = otherRepository.verifyNickname(request)
}
