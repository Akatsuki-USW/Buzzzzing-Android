package com.onewx2m.domain.usecase

import com.onewx2m.domain.repository.SpotCommentRepository
import javax.inject.Inject

class PostSpotChildrenCommentUseCase @Inject constructor(
    private val spotCommentRepository: SpotCommentRepository,
) {
    suspend operator fun invoke(
        parentId: Int,
    ) = spotCommentRepository.createChildrenComment(
        parentId = parentId,
    )
}
