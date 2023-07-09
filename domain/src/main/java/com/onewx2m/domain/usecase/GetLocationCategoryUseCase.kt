package com.onewx2m.domain.usecase

import com.onewx2m.domain.repository.CategoryRepository
import javax.inject.Inject

class GetLocationCategoryUseCase @Inject constructor(
    private val categoryRepository: CategoryRepository,
) {
    operator fun invoke() = categoryRepository.getLocationCategoryList()
}
