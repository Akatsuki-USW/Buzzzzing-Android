package com.onewx2m.domain.usecase

import com.onewx2m.domain.repository.SpotCommentRepository
import javax.inject.Inject

class GetSpotChildrenCommentsUseCase @Inject constructor(
    private val spotCommentRepository: SpotCommentRepository,
) {
    suspend operator fun invoke(
        parentId: Int,
        cursorId: Int,
    ) = spotCommentRepository.getChildrenComments(
        parentId = parentId,
        cursorId = cursorId,
    )
}
