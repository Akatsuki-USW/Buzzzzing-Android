package com.onewx2m.domain.usecase

import com.onewx2m.domain.repository.CategoryRepository
import javax.inject.Inject

class SaveEntireCategoryUseCase @Inject constructor(
    private val categoryRepository: CategoryRepository,
) {
    suspend operator fun invoke() = categoryRepository.saveEntireCategory()
}
