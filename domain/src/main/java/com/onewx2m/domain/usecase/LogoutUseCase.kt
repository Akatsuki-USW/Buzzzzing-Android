package com.onewx2m.domain.usecase

import com.onewx2m.domain.repository.AuthRepository
import com.onewx2m.domain.repository.UserRepository
import javax.inject.Inject

class LogoutUseCase @Inject constructor(
    private val userRepository: UserRepository,
) {
    suspend operator fun invoke() = userRepository.logout()
}
