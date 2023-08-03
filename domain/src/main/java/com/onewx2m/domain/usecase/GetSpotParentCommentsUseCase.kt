package com.onewx2m.domain.usecase

import com.onewx2m.domain.repository.SpotCommentRepository
import javax.inject.Inject

class GetSpotParentCommentsUseCase @Inject constructor(
    private val spotCommentRepository: SpotCommentRepository,
) {
    suspend operator fun invoke(
        spotId: Int,
        cursorId: Int,
    ) = spotCommentRepository.getParentComments(
        spotId = spotId,
        cursorId = cursorId,
    )
}
