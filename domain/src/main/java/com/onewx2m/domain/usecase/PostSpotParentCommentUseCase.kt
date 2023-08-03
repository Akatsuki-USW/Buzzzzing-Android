package com.onewx2m.domain.usecase

import com.onewx2m.domain.repository.SpotCommentRepository
import javax.inject.Inject

class PostSpotParentCommentUseCase @Inject constructor(
    private val spotCommentRepository: SpotCommentRepository,
) {
    suspend operator fun invoke(
        spotId: Int,
    ) = spotCommentRepository.createParentComment(
        spotId = spotId,
    )
}
