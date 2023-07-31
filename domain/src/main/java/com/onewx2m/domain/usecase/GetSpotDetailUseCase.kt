package com.onewx2m.domain.usecase

import com.onewx2m.domain.repository.SpotRepository
import javax.inject.Inject

class GetSpotDetailUseCase @Inject constructor(
    private val spotRepository: SpotRepository,
) {
    suspend operator fun invoke(
        spotId: Int,
    ) = spotRepository.getSpotDetail(
        spotId,
    )
}
