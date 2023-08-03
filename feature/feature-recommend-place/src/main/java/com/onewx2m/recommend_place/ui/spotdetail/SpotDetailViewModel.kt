package com.onewx2m.recommend_place.ui.spotdetail

import androidx.lifecycle.viewModelScope
import com.onewx2m.design_system.components.powermenu.PowerMenuType
import com.onewx2m.domain.Outcome
import com.onewx2m.domain.collectOutcome
import com.onewx2m.domain.exception.common.CommonException
import com.onewx2m.domain.model.SpotBookmark
import com.onewx2m.domain.model.SpotCommentList
import com.onewx2m.domain.usecase.BookmarkSpotUseCase
import com.onewx2m.domain.usecase.GetSpotDetailUseCase
import com.onewx2m.domain.usecase.GetSpotParentCommentsUseCase
import com.onewx2m.mvi.MviViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.joinAll
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SpotDetailViewModel @Inject constructor(
    private val getSpotDetailUseCase: GetSpotDetailUseCase,
    private val bookmarkSpotUseCase: BookmarkSpotUseCase,
    private val getSpotParentCommentsUseCase: GetSpotParentCommentsUseCase,
) : MviViewModel<SpotDetailViewState, SpotDetailEvent, SpotDetailSideEffect>(
    SpotDetailViewState(),
) {
    var contentPowerMenuList = listOf(PowerMenuType.REPORT.kor, PowerMenuType.BLOCK.kor)
        private set

    private var parentCommentCursorId = 0
    private var parentCommentLast = false

    fun initData(spotId: Int) = viewModelScope.launch {
        joinAll(getSpotDetail(spotId), getSpotParentCommentList(spotId))
        postEvent(SpotDetailEvent.Initialized)
    }

    private fun getSpotDetail(spotId: Int) = viewModelScope.launch {
        getSpotDetailUseCase(spotId).collectOutcome(
            handleSuccess = {
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

    fun showContentPowerMenu() = postSideEffect(SpotDetailSideEffect.ShowContentPowerMenu)
    fun goToPrevPage() = postSideEffect(SpotDetailSideEffect.GoToPrevPage)
    fun goToWriteFragment() = postSideEffect(SpotDetailSideEffect.GoToWriteFragment)
}
