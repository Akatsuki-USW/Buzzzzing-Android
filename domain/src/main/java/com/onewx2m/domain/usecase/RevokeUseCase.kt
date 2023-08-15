package com.onewx2m.domain.usecase

import com.onewx2m.domain.repository.UserRepository
import javax.inject.Inject

class RevokeUseCase @Inject constructor(
    private val userRepository: UserRepository,
) {
    suspend operator fun invoke() = userRepository.revoke()
}
