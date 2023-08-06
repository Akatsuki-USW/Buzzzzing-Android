package com.onewx2m.domain.usecase

import com.onewx2m.domain.repository.UserRepository
import javax.inject.Inject

class BlockUserUseCase @Inject constructor(
    private val userRepository: UserRepository,
) {
    suspend operator fun invoke(
        blockUserId: Int,
    ) = userRepository.block(
        blockUserId = blockUserId,
    )
}
