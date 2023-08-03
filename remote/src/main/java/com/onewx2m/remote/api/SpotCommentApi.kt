package com.onewx2m.remote.api

import com.onewx2m.remote.ApiResult
import com.onewx2m.remote.api.BuzzzzingLocationApi.Companion.CURSOR_ID
import com.onewx2m.remote.api.BuzzzzingLocationApi.Companion.LOCATION
import com.onewx2m.remote.api.SpotApi.Companion.SPOT
import com.onewx2m.remote.api.SpotApi.Companion.SPOT_ID
import com.onewx2m.remote.model.ApiResponse
import com.onewx2m.remote.model.request.CommentRequest
import com.onewx2m.remote.model.response.SpotCommentListResponse
import com.onewx2m.remote.model.response.SpotCommentResponse
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

interface SpotCommentApi {
    companion object {
        const val COMMENT = "comments"
        const val COMMENT_ID = "commentId"
        const val PARENT_ID = "parentId"
    }

    @GET("$LOCATION/$SPOT/{$SPOT_ID}/$COMMENT/parents")
    suspend fun getParentComments(
        @Path(SPOT_ID) spotId: Int,
        @Query(CURSOR_ID) cursorId: Int,
    ): ApiResult<ApiResponse<SpotCommentListResponse>>

    @POST("$LOCATION/$SPOT/{$SPOT_ID}/$COMMENT/parents")
    suspend fun createParentComment(
        @Path(SPOT_ID) spotId: Int,
        @Body request: CommentRequest,
    ): ApiResult<ApiResponse<SpotCommentResponse>>

    @PUT("$LOCATION/$SPOT/$COMMENT/{$COMMENT_ID}")
    suspend fun editComment(
        @Path(COMMENT_ID) commentId: Int,
    ): ApiResult<ApiResponse<SpotCommentResponse>>

    @DELETE("$LOCATION/$SPOT/$COMMENT/{$COMMENT_ID}")
    suspend fun deleteComment(
        @Path(COMMENT_ID) commentId: Int,
    ): ApiResult<ApiResponse<SpotCommentResponse>>

    @GET("$LOCATION/$SPOT/$COMMENT/{$PARENT_ID}/children")
    suspend fun getChildrenComments(
        @Path(PARENT_ID) parentId: Int,
        @Query(CURSOR_ID) cursorId: Int,
    ): ApiResult<ApiResponse<SpotCommentListResponse>>

    @POST("$LOCATION/$SPOT/$COMMENT/{$PARENT_ID}/children")
    suspend fun createChildrenComment(
        @Path(PARENT_ID) parentId: Int,
        @Body request: CommentRequest,
    ): ApiResult<ApiResponse<SpotCommentResponse>>
}
