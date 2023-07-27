package com.onewx2m.domain.usecase

import com.onewx2m.domain.repository.SpotRepository
import java.io.File
import javax.inject.Inject

class PostSpotUseCase @Inject constructor(
    private val spotRepository: SpotRepository,
) {
    suspend operator fun invoke(
        spotCategoryId: Int,
        locationId: Int,
        title: String,
        address: String,
        content: String,
        imageFiles: List<File>,
    ) =
        spotRepository.postSpot(
            spotCategoryId = spotCategoryId,
            locationId = locationId,
            title = title,
            address = address,
            content = content,
            imageFiles = imageFiles,
        )
}
