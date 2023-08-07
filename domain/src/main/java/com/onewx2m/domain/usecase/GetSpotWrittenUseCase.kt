package com.onewx2m.domain.usecase

import com.onewx2m.domain.repository.UserRepository
import javax.inject.Inject

class GetSpotWrittenUseCase @Inject constructor(
    private val userRepository: UserRepository,
) {
    suspend operator fun invoke(
        cursorId: Int,
    ) = userRepository.getSpotWritten(
        cursorId,
    )
}
