package com.onewx2m.recommend_place.ui.spotdetail

import android.view.View
import androidx.lifecycle.viewModelScope
import com.onewx2m.design_system.components.powermenu.PowerMenuType
import com.onewx2m.design_system.components.recyclerview.spotcomment.children.SpotChildrenCommentItem
import com.onewx2m.design_system.components.recyclerview.spotcomment.parent.SpotParentCommentItem
import com.onewx2m.domain.Outcome
import com.onewx2m.domain.collectOutcome
import com.onewx2m.domain.exception.common.CommonException
import com.onewx2m.domain.model.SpotBookmark
import com.onewx2m.domain.model.SpotCommentList
import com.onewx2m.domain.usecase.BookmarkSpotUseCase
import com.onewx2m.domain.usecase.GetSpotChildrenCommentsUseCase
import com.onewx2m.domain.usecase.GetSpotDetailUseCase
import com.onewx2m.domain.usecase.GetSpotParentCommentsUseCase
import com.onewx2m.mvi.MviViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.joinAll
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SpotDetailViewModel @Inject constructor(
    private val getSpotDetailUseCase: GetSpotDetailUseCase,
    private val bookmarkSpotUseCase: BookmarkSpotUseCase,
    private val getSpotParentCommentsUseCase: GetSpotParentCommentsUseCase,
    private val getSpotChildrenCommentsUseCase: GetSpotChildrenCommentsUseCase,
) : MviViewModel<SpotDetailViewState, SpotDetailEvent, SpotDetailSideEffect>(
    SpotDetailViewState(),
) {
    var contentPowerMenuList = listOf(PowerMenuType.REPORT.kor, PowerMenuType.BLOCK.kor)
        private set

    private var parentCommentCursorId = 0
    private var parentCommentLast = false

    private var authorId = -1

    // key == parentCommentId
    private val childrenCommentQuery = mutableMapOf<Int, ChildrenCommentQuery>()

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

        postEvent(SpotDetailEvent.UpdateSpotParentCommentList(outcome.data.content.map { it.toSpotParentCommentItem() }))
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
                    visibleChildrenCommentList = outcome.data.content.map {
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
        )

        is SpotDetailEvent.UpdateSpotParentCommentList -> current.copy(
            spotCommentList = event.commentList,
        )

        SpotDetailEvent.HideSmallLoadingLottie -> current.copy(
            isSmallLottieVisible = false,
        )

        SpotDetailEvent.ShowSmallLoadingLottie -> current.copy(
            isSmallLottieVisible = true,
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

    fun showContentPowerMenu() =
        postSideEffect(SpotDetailSideEffect.ShowContentPowerMenu)

    fun showParentCommentPowerMenu(view: View, item: SpotParentCommentItem) =
        postSideEffect(SpotDetailSideEffect.ShowParentCommentPowerMenu(view, item))

    fun showChildrenCommentPowerMenu(view: View, item: SpotChildrenCommentItem) =
        postSideEffect(SpotDetailSideEffect.ShowChildCommentPowerMenu(view, item))

    fun goToPrevPage() = postSideEffect(SpotDetailSideEffect.GoToPrevPage)
    fun goToWriteFragment() = postSideEffect(SpotDetailSideEffect.GoToWriteFragment)
}

data class ChildrenCommentQuery(
    val cursorId: Int = 0,
    val last: Boolean = false,
)
