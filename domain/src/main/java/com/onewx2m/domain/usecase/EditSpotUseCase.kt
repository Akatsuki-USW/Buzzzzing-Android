package com.onewx2m.domain.usecase

import com.onewx2m.domain.repository.SpotRepository
import java.io.File
import javax.inject.Inject

class EditSpotUseCase @Inject constructor(
    private val spotRepository: SpotRepository,
) {
    suspend operator fun invoke(
        spotId: Int,
        spotCategoryId: Int,
        locationId: Int,
        title: String,
        address: String,
        content: String,
        imageFiles: List<File>,
        deletedUrls: List<String>,
        previousUrls: List<String>,
    ) =
        spotRepository.editSpot(
            spotId = spotId,
            spotCategoryId = spotCategoryId,
            locationId = locationId,
            title = title,
            address = address,
            content = content,
            imageFiles = imageFiles,
            deletedUrls = deletedUrls,
            previousUrls = previousUrls,
        )
}
