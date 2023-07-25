package com.onewx2m.domain.usecase

import com.onewx2m.domain.repository.SpotRepository
import javax.inject.Inject

class GetAllSpotListUseCase @Inject constructor(
    private val spotRepository: SpotRepository,
) {
    suspend operator fun invoke(
        cursorId: Int,
        categoryId: Int? = null,
    ) = spotRepository.getAllSpotList(
        cursorId,
        categoryId,
    )
}
