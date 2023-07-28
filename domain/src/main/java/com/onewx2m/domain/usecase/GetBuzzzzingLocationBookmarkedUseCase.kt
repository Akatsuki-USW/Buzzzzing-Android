package com.onewx2m.domain.usecase

import com.onewx2m.domain.repository.BuzzzzingLocationRepository
import javax.inject.Inject

class GetBuzzzzingLocationBookmarkedUseCase @Inject constructor(
    private val buzzzzingLocationRepository: BuzzzzingLocationRepository,
) {
    suspend operator fun invoke(
        cursorId: Int,
    ) = buzzzzingLocationRepository.getBuzzzzingLocationBookmarked(
        cursorId = cursorId,
    )
}
