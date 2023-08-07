package com.onewx2m.domain.usecase

import com.onewx2m.domain.repository.UserRepository
import javax.inject.Inject

class GetSpotCommentedUseCase @Inject constructor(
    private val userRepository: UserRepository,
) {
    suspend operator fun invoke(
        cursorId: Int,
    ) = userRepository.getSpotCommented(
        cursorId,
    )
}
