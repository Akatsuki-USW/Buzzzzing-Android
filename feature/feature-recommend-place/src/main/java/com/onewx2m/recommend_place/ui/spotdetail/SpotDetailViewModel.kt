package com.onewx2m.recommend_place.ui.spotdetail

import android.view.View
import androidx.lifecycle.viewModelScope
import com.onewx2m.core_ui.model.WriteContent
import com.onewx2m.design_system.components.powermenu.PowerMenuType
import com.onewx2m.design_system.components.recyclerview.spotcomment.SpotCommentType
import com.onewx2m.design_system.components.recyclerview.spotcomment.children.SpotChildrenCommentItem
import com.onewx2m.design_system.components.recyclerview.spotcomment.parent.SpotParentCommentItem
import com.onewx2m.domain.Outcome
import com.onewx2m.domain.collectOutcome
import com.onewx2m.domain.enums.ReportType
import com.onewx2m.domain.exception.common.CommonException
import com.onewx2m.domain.model.SpotBookmark
import com.onewx2m.domain.model.SpotComment
import com.onewx2m.domain.model.SpotCommentList
import com.onewx2m.domain.usecase.BlockUserUseCase
import com.onewx2m.domain.usecase.BookmarkSpotUseCase
import com.onewx2m.domain.usecase.DeleteCommentUseCase
import com.onewx2m.domain.usecase.EditCommentUseCase
import com.onewx2m.domain.usecase.GetSpotChildrenCommentsUseCase
import com.onewx2m.domain.usecase.GetSpotDetailUseCase
import com.onewx2m.domain.usecase.GetSpotParentCommentsUseCase
import com.onewx2m.domain.usecase.PostSpotChildrenCommentUseCase
import com.onewx2m.domain.usecase.PostSpotParentCommentUseCase
import com.onewx2m.domain.usecase.ReportUseCase
import com.onewx2m.mvi.MviViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.joinAll
import kotlinx.coroutines.launch
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import javax.inject.Inject

@HiltViewModel
class SpotDetailViewModel @Inject constructor(
    private val getSpotDetailUseCase: GetSpotDetailUseCase,
    private val bookmarkSpotUseCase: BookmarkSpotUseCase,
    private val getSpotParentCommentsUseCase: GetSpotParentCommentsUseCase,
    private val getSpotChildrenCommentsUseCase: GetSpotChildrenCommentsUseCase,
    private val postSpotParentCommentUseCase: PostSpotParentCommentUseCase,
    private val postSpotChildrenCommentUseCase: PostSpotChildrenCommentUseCase,
    private val deleteCommentUseCase: DeleteCommentUseCase,
    private val editCommentUseCase: EditCommentUseCase,
    private val blockUserUseCase: BlockUserUseCase,
    private val reportUseCase: ReportUseCase,
) : MviViewModel<SpotDetailViewState, SpotDetailEvent, SpotDetailSideEffect>(
    SpotDetailViewState(),
) {
    var contentPowerMenuList = listOf(PowerMenuType.REPORT.kor, PowerMenuType.BLOCK.kor)
        private set

    private var parentCommentCursorId = 0
    private var parentCommentLast = false

    var authorId = -1
        private set

    private var replyCommentId: Int? = null

    // key == parentCommentId
    private val childrenCommentQuery = mutableMapOf<Int, ChildrenCommentQuery>()

    fun reportComment(targetId: Int, userId: Int, content: String) {
        report(ReportType.COMMENT, targetId, userId, content)
    }

    fun reportSpot(targetId: Int, userId: Int, content: String) {
        report(ReportType.SPOT, targetId, userId, content)
    }

    private fun report(reportType: ReportType, targetId: Int, userId: Int, content: String) =
        viewModelScope.launch {
            reportUseCase(
                reportType = reportType,
                reportTargetId = targetId,
                reportedUserId = userId,
                content = content,
            ).onStart { postEvent(SpotDetailEvent.ShowSmallLoadingLottie) }
                .onCompletion {
                    postEvent(SpotDetailEvent.HideSmallLoadingLottie)
                }.collectOutcome(
                    handleSuccess = ::handleReportSuccess,
                    handleFail = { handleError(it.error) },
                )
        }

    private fun handleReportSuccess(outcome: Outcome.Success<Unit>) {
        postSideEffect(SpotDetailSideEffect.ShowReportSuccessToast)
    }

    fun blockUser(userId: Int) = viewModelScope.launch {
        blockUserUseCase(userId).onStart { postEvent(SpotDetailEvent.ShowSmallLoadingLottie) }
            .onCompletion {
                postEvent(SpotDetailEvent.HideSmallLoadingLottie)
            }.collectOutcome(
                handleSuccess = ::handleBlockUserSuccess,
                handleFail = { handleError(it.error) },
            )
    }

    private fun handleBlockUserSuccess(outcome: Outcome.Success<Unit>) {
        postSideEffect(SpotDetailSideEffect.GoToHomeFragment)
    }

    fun editComment(commentId: Int, content: String) = viewModelScope.launch {
        editCommentUseCase(commentId, content).onStart {
            postEvent(SpotDetailEvent.ShowSmallLoadingLottie)
        }.onCompletion {
            postEvent(SpotDetailEvent.HideSmallLoadingLottie)
        }.collectOutcome(
            handleSuccess = { handleEditCommentSuccess(commentId, content) },
            handleFail = { handleError(it.error) },
        )
    }

    private fun handleEditCommentSuccess(commentId: Int, content: String) {
        val commentList = state.value.spotCommentList.map { comment ->
            if (comment.id == commentId) {
                comment.copy(content = content)
            } else {
                val childrenCommentList = comment.visibleChildrenCommentList.map { child ->
                    if (child.id == commentId) {
                        child.copy(content = content)
                    } else {
                        child
                    }
                }

                comment.copy(visibleChildrenCommentList = childrenCommentList)
            }
        }

        postEvent(SpotDetailEvent.UpdateSpotParentCommentList(commentList))
    }

    fun deleteComment(commentId: Int) = viewModelScope.launch {
        deleteCommentUseCase(commentId).onStart {
            postEvent(SpotDetailEvent.ShowSmallLoadingLottie)
        }.onCompletion {
            postEvent(SpotDetailEvent.HideSmallLoadingLottie)
        }.collectOutcome(
            handleSuccess = { handleDeleteCommentSuccess(commentId) },
            handleFail = { handleError(it.error) },
        )
    }

    private fun handleDeleteCommentSuccess(commentId: Int) {
        val commentList = state.value.spotCommentList.map { comment ->
            if (comment.id == commentId) {
                comment.copy(type = SpotCommentType.DELETE)
            } else {
                val childrenCommentList = comment.visibleChildrenCommentList.map { child ->
                    if (child.id == commentId) {
                        child.copy(type = SpotCommentType.DELETE)
                    } else {
                        child
                    }
                }

                comment.copy(visibleChildrenCommentList = childrenCommentList)
            }
        }

        postEvent(SpotDetailEvent.UpdateSpotParentCommentList(commentList))
    }

    fun onClickCommentSend(spotId: Int) {
        if (state.value.commentContent.isEmpty()) return

        postEvent(SpotDetailEvent.ShowSmallLoadingLottie)
        postEvent(SpotDetailEvent.HideReplyLayout)
        postSideEffect(SpotDetailSideEffect.HideKeyboard)

        if (replyCommentId == null) {
            createParentComment(spotId)
        } else {
            createChildComment()
        }
    }

    private fun createParentComment(spotId: Int) = viewModelScope.launch {
        postSpotParentCommentUseCase(spotId, state.value.commentContent).onCompletion {
            postEvent(SpotDetailEvent.HideSmallLoadingLottie)
            updateContent("", true)
        }.collectOutcome(
            handleSuccess = ::handleCreateParentCommentSuccess,
            handleFail = { handleError(it.error) },
        )
    }

    private fun handleCreateParentCommentSuccess(outcome: Outcome.Success<SpotComment>) {
        if (parentCommentLast) {
            val commentList =
                state.value.spotCommentList.plus(outcome.data.toSpotParentCommentItem())
            postEvent(SpotDetailEvent.UpdateSpotParentCommentList(commentList))
        }

        val spotDetailContent = state.value.spotDetailContent.copy(
            commentCount = state.value.spotDetailContent.commentCount + 1,
        )
        postEvent(SpotDetailEvent.UpdateSpotDetailContent(spotDetailContent))
    }

    private fun createChildComment() = viewModelScope.launch {
        postSpotChildrenCommentUseCase(replyCommentId!!, state.value.commentContent).onCompletion {
            postEvent(SpotDetailEvent.HideSmallLoadingLottie)
            updateContent("", true)
            replyCommentId = null
        }.collectOutcome(
            handleSuccess = ::handleCreateChildCommentSuccess,
            handleFail = { handleError(it.error) },
        )
    }

    private fun handleCreateChildCommentSuccess(outcome: Outcome.Success<SpotComment>) {
        val childComment = outcome.data.toSpotChildrenCommentItem(replyCommentId!!)

        val parentComment = state.value.spotCommentList.find { it.id == replyCommentId } ?: return

        val childComments =
            if (childrenCommentQuery[replyCommentId!!]?.last == true) {
                parentComment.visibleChildrenCommentList.plus(
                    childComment,
                )
            } else {
                parentComment.visibleChildrenCommentList
            }

        val commentList =
            state.value.spotCommentList.map { comment ->
                if (comment.id == replyCommentId) {
                    comment.copy(
                        visibleChildrenCommentList = childComments,
                        totalChildrenCount = comment.totalChildrenCount + 1,
                    )
                } else {
                    comment
                }
            }

        postEvent(SpotDetailEvent.UpdateSpotParentCommentList(commentList))
    }

    fun updateContent(content: String, needRender: Boolean = false) {
        postEvent(
            SpotDetailEvent.UpdateComment(content, needRender),
        )
    }

    fun onClickReply(replyCommentId: Int, nickname: String) {
        this.replyCommentId = replyCommentId
        postSideEffect(SpotDetailSideEffect.ShowKeyboard)
        postEvent(SpotDetailEvent.ShowReplyLayout(nickname))
    }

    fun onReplyCloseClick() {
        this.replyCommentId = null
        postEvent(SpotDetailEvent.HideReplyLayout)
    }

    fun getParentCommentPowerMenuList(isAuthor: Boolean) =
        if (isAuthor) {
            listOf(
                PowerMenuType.EDIT.kor,
                PowerMenuType.DELETE.kor,
                PowerMenuType.REPLY.kor,
            )
        } else {
            listOf(
                PowerMenuType.REPORT.kor,
                PowerMenuType.BLOCK.kor,
                PowerMenuType.REPLY.kor,
            )
        }

    fun getChildrenCommentPowerMenuList(isAuthor: Boolean) =
        getParentCommentPowerMenuList(isAuthor).filterNot { it == PowerMenuType.REPLY.kor }

    fun initData(spotId: Int) = viewModelScope.launch {
        joinAll(getSpotDetail(spotId), getSpotParentCommentList(spotId))
        postEvent(SpotDetailEvent.Initialized)
    }

    private fun getSpotDetail(spotId: Int) = viewModelScope.launch {
        getSpotDetailUseCase(spotId).collectOutcome(
            handleSuccess = {
                authorId = it.data.userId
                if (it.data.isAuthor) {
                    contentPowerMenuList =
                        listOf(PowerMenuType.EDIT.kor, PowerMenuType.DELETE.kor)
                }
                postEvent(
                    SpotDetailEvent.UpdateSpotDetailContent(
                        it.data.toSpotDetailContentItem(),
                    ),
                )
            },
            handleFail = { handleError(it.error) },
        )
    }

    fun getSpotParentCommentList(spotId: Int) = viewModelScope.launch {
        if (parentCommentLast) return@launch

        getSpotParentCommentsUseCase(spotId, parentCommentCursorId).collectOutcome(
            handleSuccess = ::handleSpotParentCommentListSuccess,
            handleFail = { handleError(it.error) },
        )
    }

    private fun handleSpotParentCommentListSuccess(outcome: Outcome.Success<SpotCommentList>) {
        parentCommentCursorId = outcome.data.content.lastOrNull()?.id ?: -1
        parentCommentLast = outcome.data.last

        val newCommentList =
            outcome.data.content.map { it.toSpotParentCommentItem() } + if (parentCommentLast) {
                listOf()
            } else {
                listOf(
                    SpotParentCommentItem(type = SpotCommentType.LOADING),
                )
            }

        val commentList =
            state.value.spotCommentList.minus(SpotParentCommentItem(type = SpotCommentType.LOADING)) + newCommentList

        postEvent(SpotDetailEvent.UpdateSpotParentCommentList(commentList))
        postEvent(
            SpotDetailEvent.UpdateSpotDetailContent(
                state.value.spotDetailContent.copy(
                    commentCount = outcome.data.totalElements,
                ),
            ),
        )
    }

    fun bookmark(spotId: Int) = viewModelScope.launch {
        bookmarkSpotUseCase(spotId).collectOutcome(
            handleSuccess = ::handleBookmarkSuccess,
            handleFail = { handleError(it.error) },
        )
    }

    fun getChildrenComments(parentId: Int) = viewModelScope.launch {
        val (cursorId, last) = childrenCommentQuery.getOrPut(parentId) {
            ChildrenCommentQuery()
        }
        getSpotChildrenCommentsUseCase(parentId, cursorId).onStart {
            postEvent(SpotDetailEvent.ShowSmallLoadingLottie)
        }.onCompletion {
            postEvent(SpotDetailEvent.HideSmallLoadingLottie)
        }.collectOutcome(
            handleSuccess = { handleSpotChildrenCommentListSuccess(parentId, it) },
            handleFail = { handleError(it.error) },
        )
    }

    private fun handleSpotChildrenCommentListSuccess(
        parentId: Int,
        outcome: Outcome.Success<SpotCommentList>,
    ) {
        childrenCommentQuery[parentId] = ChildrenCommentQuery(
            outcome.data.content.lastOrNull()?.id ?: -1,
            outcome.data.last,
        )

        val commentList = state.value.spotCommentList.map { parentComment ->
            if (parentComment.id == parentId) {
                parentComment.copy(
                    visibleChildrenCommentList = parentComment.visibleChildrenCommentList + outcome.data.content.map {
                        it.toSpotChildrenCommentItem(
                            parentId,
                        )
                    },
                    totalChildrenCount = outcome.data.totalElements,
                )
            } else {
                parentComment
            }
        }

        postEvent(SpotDetailEvent.UpdateSpotParentCommentList(commentList))
    }

    private fun handleBookmarkSuccess(
        outcome: Outcome.Success<SpotBookmark>,
    ) {
        val spotUpdatedItem = state.value.spotDetailContent.copy(
            isBookmarked = outcome.data.isBookmarked,
        )

        postEvent(
            SpotDetailEvent.UpdateSpotDetailContent(
                spotUpdatedItem,
            ),
        )
    }

    override fun reduceState(
        current: SpotDetailViewState,
        event: SpotDetailEvent,
    ): SpotDetailViewState = when (event) {
        SpotDetailEvent.Initialized -> current.copy(
            isRecyclerViewVisible = true,
            isLoadingLottieVisible = false,
        )

        is SpotDetailEvent.UpdateSpotDetailContent -> current.copy(
            spotDetailContent = event.content,
            needCommentRender = false,
        )

        is SpotDetailEvent.UpdateSpotParentCommentList -> current.copy(
            spotCommentList = event.commentList,
            needCommentRender = false,
        )

        SpotDetailEvent.HideSmallLoadingLottie -> current.copy(
            isSmallLottieVisible = false,
            needCommentRender = false,
        )

        SpotDetailEvent.ShowSmallLoadingLottie -> current.copy(
            isSmallLottieVisible = true,
            needCommentRender = false,
        )

        SpotDetailEvent.HideReplyLayout -> current.copy(
            isReplyLayoutVisible = false,
            replyNickname = "",
            needCommentRender = false,
        )

        is SpotDetailEvent.ShowReplyLayout -> current.copy(
            isReplyLayoutVisible = true,
            replyNickname = event.nickname,
            needCommentRender = false,
        )

        is SpotDetailEvent.UpdateComment -> current.copy(
            commentContent = event.content,
            needCommentRender = event.needRender,
        )
    }

    private fun handleError(error: Throwable?) {
        when (error) {
            is CommonException.NeedLoginException -> {
                postSideEffect(SpotDetailSideEffect.ShowErrorToast(error.snackBarMessage))
                postSideEffect(SpotDetailSideEffect.GoToLoginFragment)
            }

            else -> postSideEffect(
                SpotDetailSideEffect.ShowErrorToast(
                    error?.message ?: CommonException.UnknownException().snackBarMessage,
                ),
            )
        }
    }

    fun showBlockDialog(userId: Int) = postSideEffect(SpotDetailSideEffect.ShowBlockDialog(userId))
    fun showDeleteSpotDialog() = postSideEffect(SpotDetailSideEffect.ShowSpotDeleteDialog)
    fun showDeleteCommentDialog(commentId: Int) =
        postSideEffect(SpotDetailSideEffect.ShowCommentDeleteDialog(commentId))

    fun showContentPowerMenu() =
        postSideEffect(SpotDetailSideEffect.ShowContentPowerMenu)

    fun showParentCommentPowerMenu(view: View, item: SpotParentCommentItem) =
        postSideEffect(SpotDetailSideEffect.ShowParentCommentPowerMenu(view, item))

    fun showChildrenCommentPowerMenu(view: View, item: SpotChildrenCommentItem) =
        postSideEffect(SpotDetailSideEffect.ShowChildCommentPowerMenu(view, item))

    fun showCommentBottomSheet(content: String, commentId: Int) =
        postSideEffect(SpotDetailSideEffect.ShowEditCommentBottomSheet(content, commentId))

    fun showSpotReportBottomSheet(spotId: Int) =
        postSideEffect(
            SpotDetailSideEffect.ShowSpotReportBottomSheet(
                userId = authorId,
                reportId = spotId,
            ),
        )

    fun showCommentReportBottomSheet(userId: Int, commentId: Int) =
        postSideEffect(
            SpotDetailSideEffect.ShowCommentReportBottomSheet(
                userId = userId,
                reportId = commentId,
            ),
        )

    fun goToPrevPage() = postSideEffect(SpotDetailSideEffect.GoToPrevPage)
    fun goToWriteFragment(spotId: Int) {
        val state = state.value.spotDetailContent
        val writeContent = WriteContent(
            spotId = spotId,
            title = state.title,
            buzzzzingLocation = state.location,
            buzzzzingLocationId = state.locationId,
            address = state.address,
            spotCategoryId = state.spotCategoryId,
            imgUrls = state.imageUrls,
            content = state.content,
        )

        postSideEffect(SpotDetailSideEffect.GoToWriteFragment(writeContent))
    }
}

data class ChildrenCommentQuery(
    val cursorId: Int = 0,
    val last: Boolean = false,
)
