package com.onewx2m.domain.usecase

import com.onewx2m.domain.repository.KakaoLocationRepository
import javax.inject.Inject

class GetKakaoLocationUseCase @Inject constructor(
    private val kakaoLocationRepository: KakaoLocationRepository,
) {
    suspend operator fun invoke(
        query: String,
        page: Int,
    ) = kakaoLocationRepository.getKakaoLocation(
        query = query,
        page = page,
    )
}
