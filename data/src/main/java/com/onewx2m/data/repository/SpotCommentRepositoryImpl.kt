package com.onewx2m.data.repository

import com.onewx2m.data.datasource.RemoteSpotCommentDataSource
import com.onewx2m.data.extension.flatMapOutcomeSuccess
import com.onewx2m.data.model.toDomain
import com.onewx2m.domain.Outcome
import com.onewx2m.domain.model.SpotComment
import com.onewx2m.domain.model.SpotCommentList
import com.onewx2m.domain.repository.SpotCommentRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SpotCommentRepositoryImpl @Inject constructor(
    private val remoteSpotCommentDataSource: RemoteSpotCommentDataSource,
) : SpotCommentRepository {
    override suspend fun getParentComments(
        spotId: Int,
        cursorId: Int,
    ): Flow<Outcome<SpotCommentList>> {
        return remoteSpotCommentDataSource.getParentComments(spotId = spotId, cursorId = cursorId)
            .flatMapOutcomeSuccess {
                it.toDomain()
            }
    }

    override suspend fun createParentComment(spotId: Int): Flow<Outcome<SpotComment>> {
        return remoteSpotCommentDataSource.createParentComment(spotId = spotId)
            .flatMapOutcomeSuccess {
                it.toDomain()
            }
    }

    override suspend fun editComment(commentId: Int): Flow<Outcome<SpotComment>> {
        return remoteSpotCommentDataSource.editComment(commentId = commentId)
            .flatMapOutcomeSuccess {
                it.toDomain()
            }
    }

    override suspend fun deleteComment(commentId: Int): Flow<Outcome<SpotComment>> {
        return remoteSpotCommentDataSource.deleteComment(commentId = commentId)
            .flatMapOutcomeSuccess {
                it.toDomain()
            }
    }

    override suspend fun getChildrenComments(
        parentId: Int,
        cursorId: Int,
    ): Flow<Outcome<SpotCommentList>> {
        return remoteSpotCommentDataSource.getChildrenComments(parentId = parentId, cursorId = cursorId)
            .flatMapOutcomeSuccess {
                it.toDomain()
            }
    }

    override suspend fun createChildrenComment(parentId: Int): Flow<Outcome<SpotComment>> {
        return remoteSpotCommentDataSource.createChildrenComment(parentId = parentId)
            .flatMapOutcomeSuccess {
                it.toDomain()
            }
    }
}
