package com.onewx2m.domain.usecase

import com.onewx2m.domain.repository.BuzzzzingLocationRepository
import javax.inject.Inject

class GetBuzzzzingLocationTop5UseCase @Inject constructor(
    private val buzzzzingLocationRepository: BuzzzzingLocationRepository,
) {
    suspend operator fun invoke() = buzzzzingLocationRepository.getBuzzzzingLocationTop5()
}
