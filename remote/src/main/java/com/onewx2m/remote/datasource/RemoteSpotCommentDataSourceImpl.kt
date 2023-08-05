package com.onewx2m.remote.datasource

import com.onewx2m.data.datasource.RemoteSpotCommentDataSource
import com.onewx2m.data.model.SpotCommentEntity
import com.onewx2m.data.model.SpotCommentListEntity
import com.onewx2m.domain.Outcome
import com.onewx2m.remote.api.SpotCommentApi
import com.onewx2m.remote.model.request.CommentRequest
import com.onewx2m.remote.model.response.toEntity
import com.onewx2m.remote.onFailure
import com.onewx2m.remote.onSuccess
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class RemoteSpotCommentDataSourceImpl @Inject constructor(
    private val api: SpotCommentApi,
) : RemoteSpotCommentDataSource {
    override suspend fun getParentComments(
        spotId: Int,
        cursorId: Int,
    ): Flow<Outcome<SpotCommentListEntity>> = flow {
        api.getParentComments(
            spotId = spotId,
            cursorId = cursorId,
        ).onSuccess { body ->
            emit(Outcome.Success(body.data!!.toEntity()))
        }.onFailure {
            emit(Outcome.Failure(it))
        }
    }

    override suspend fun createParentComment(
        spotId: Int,
        content: String,
    ): Flow<Outcome<SpotCommentEntity>> = flow {
        api.createParentComment(spotId = spotId, CommentRequest(content)).onSuccess { body ->
            emit(Outcome.Success(body.data!!.toEntity()))
        }.onFailure {
            emit(Outcome.Failure(it))
        }
    }

    override suspend fun editComment(
        commentId: Int,
        content: String,
    ): Flow<Outcome<SpotCommentEntity>> = flow {
        api.editComment(commentId = commentId, request = CommentRequest(content)).onSuccess { body ->
            emit(Outcome.Success(body.data!!.toEntity()))
        }.onFailure {
            emit(Outcome.Failure(it))
        }
    }

    override suspend fun deleteComment(commentId: Int): Flow<Outcome<SpotCommentEntity>> = flow {
        api.deleteComment(commentId = commentId).onSuccess { body ->
            emit(Outcome.Success(body.data!!.toEntity()))
        }.onFailure {
            emit(Outcome.Failure(it))
        }
    }

    override suspend fun getChildrenComments(
        parentId: Int,
        cursorId: Int,
    ): Flow<Outcome<SpotCommentListEntity>> = flow {
        api.getChildrenComments(parentId = parentId, cursorId = cursorId).onSuccess { body ->
            emit(Outcome.Success(body.data!!.toEntity()))
        }.onFailure {
            emit(Outcome.Failure(it))
        }
    }

    override suspend fun createChildrenComment(
        parentId: Int,
        content: String,
    ): Flow<Outcome<SpotCommentEntity>> =
        flow {
            api.createChildrenComment(parentId = parentId, CommentRequest(content))
                .onSuccess { body ->
                    emit(Outcome.Success(body.data!!.toEntity()))
                }.onFailure {
                    emit(Outcome.Failure(it))
                }
        }
}
