package com.onewx2m.domain.usecase

import com.onewx2m.domain.repository.SpotCommentRepository
import javax.inject.Inject

class EditCommentUseCase @Inject constructor(
    private val spotCommentRepository: SpotCommentRepository,
) {
    suspend operator fun invoke(
        commentId: Int,
        content: String,
    ) = spotCommentRepository.editComment(
        commentId = commentId,
        content = content,
    )
}
