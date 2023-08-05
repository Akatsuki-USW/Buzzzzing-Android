package com.onewx2m.domain.repository

import com.onewx2m.domain.Outcome
import com.onewx2m.domain.model.SpotComment
import com.onewx2m.domain.model.SpotCommentList
import kotlinx.coroutines.flow.Flow

interface SpotCommentRepository {
    suspend fun getParentComments(
        spotId: Int,
        cursorId: Int,
    ): Flow<Outcome<SpotCommentList>>

    suspend fun createParentComment(
        spotId: Int,
        content: String,
    ): Flow<Outcome<SpotComment>>

    suspend fun editComment(
        commentId: Int,
        content: String,
    ): Flow<Outcome<SpotComment>>

    suspend fun deleteComment(
        commentId: Int,
    ): Flow<Outcome<SpotComment>>

    suspend fun getChildrenComments(
        parentId: Int,
        cursorId: Int,
    ): Flow<Outcome<SpotCommentList>>

    suspend fun createChildrenComment(
        parentId: Int,
        content: String,
    ): Flow<Outcome<SpotComment>>
}
