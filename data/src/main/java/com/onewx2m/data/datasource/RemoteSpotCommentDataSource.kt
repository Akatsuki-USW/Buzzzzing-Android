package com.onewx2m.data.datasource

import com.onewx2m.data.model.SpotCommentEntity
import com.onewx2m.data.model.SpotCommentListEntity
import com.onewx2m.domain.Outcome
import com.onewx2m.domain.model.SpotComment
import com.onewx2m.domain.model.SpotCommentList
import kotlinx.coroutines.flow.Flow

interface RemoteSpotCommentDataSource {
    suspend fun getParentComments(
        spotId: Int,
        cursorId: Int,
    ): Flow<Outcome<SpotCommentListEntity>>

    suspend fun createParentComment(
        spotId: Int,
        content: String,
    ): Flow<Outcome<SpotCommentEntity>>

    suspend fun editComment(
        commentId: Int,
    ): Flow<Outcome<SpotCommentEntity>>

    suspend fun deleteComment(
        commentId: Int,
    ): Flow<Outcome<SpotCommentEntity>>

    suspend fun getChildrenComments(
        parentId: Int,
        cursorId: Int,
    ): Flow<Outcome<SpotCommentListEntity>>

    suspend fun createChildrenComment(
        parentId: Int,
        content: String,
    ): Flow<Outcome<SpotCommentEntity>>
}
