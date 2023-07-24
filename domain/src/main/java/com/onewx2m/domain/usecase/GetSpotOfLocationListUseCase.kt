package com.onewx2m.domain.usecase

import com.onewx2m.domain.repository.SpotRepository
import javax.inject.Inject

class GetSpotOfLocationListUseCase @Inject constructor(
    private val spotRepository: SpotRepository,
) {
    suspend operator fun invoke(
        cursorId: Int,
        locationId: Int,
        categoryId: Int? = null,
    ) = spotRepository.getSpotOfLocationList(
        cursorId,
        locationId,
        categoryId,
    )
}
