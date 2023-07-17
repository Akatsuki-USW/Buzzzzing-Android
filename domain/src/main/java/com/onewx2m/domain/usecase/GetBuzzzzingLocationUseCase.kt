package com.onewx2m.domain.usecase

import com.onewx2m.domain.repository.BuzzzzingLocationRepository
import javax.inject.Inject

class GetBuzzzzingLocationUseCase @Inject constructor(
    private val buzzzzingLocationRepository: BuzzzzingLocationRepository,
) {
    suspend operator fun invoke(
        cursorId: Int,
        keyword: String? = null,
        categoryId: Int? = null,
        congestionSort: String,
        cursorCongestionLevel: Int?,
    ) = buzzzzingLocationRepository.getBuzzzzingLocation(
        cursorId = cursorId,
        keyword = keyword,
        categoryId = categoryId,
        congestionSort = congestionSort,
        cursorCongestionLevel = cursorCongestionLevel,
    )
}
