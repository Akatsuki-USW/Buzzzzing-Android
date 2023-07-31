package com.onewx2m.recommend_place.ui.spotdetail

import androidx.lifecycle.viewModelScope
import com.onewx2m.domain.collectOutcome
import com.onewx2m.domain.exception.common.CommonException
import com.onewx2m.domain.model.SpotDetail
import com.onewx2m.domain.usecase.GetSpotDetailUseCase
import com.onewx2m.mvi.MviViewModel
import com.onewx2m.recommend_place.ui.spotdetail.adapter.SpotDetailContentItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SpotDetailViewModel @Inject constructor(
    private val getSpotDetailUseCase: GetSpotDetailUseCase,
) : MviViewModel<SpotDetailViewState, SpotDetailEvent, SpotDetailSideEffect>(
    SpotDetailViewState(),
) {
    fun getSpotDetail(spotId: Int) = viewModelScope.launch {
        getSpotDetailUseCase(spotId).collectOutcome(
            handleSuccess = {
                postEvent(
                    SpotDetailEvent.UpdateSpotDetailContent(
                        it.data.toSpotDetailContentItem(),
                    ),
                )
                postEvent(SpotDetailEvent.Initialized)
            },
            handleFail = { handleError(it.error) },
        )
    }

    private fun SpotDetail.toSpotDetailContentItem() = SpotDetailContentItem(
        spotId = id,
        profileImageUrl = userProfileImageUrl ?: "",
        nickname = userNickname,
        createdAt = createdAt,
        isBookmarked = isBookmarked,
        title = title,
        location = locationName,
        address = address,
        imageUrls = imageUrls,
        content = content,
        commentCount = 0, // TODO 업데이트
    )

    override fun reduceState(
        current: SpotDetailViewState,
        event: SpotDetailEvent,
    ): SpotDetailViewState = when(event) {
        SpotDetailEvent.Initialized -> current.copy(
            isRecyclerViewVisible = true,
            isLoadingLottieVisible = false,
        )
        is SpotDetailEvent.UpdateSpotDetailContent -> current.copy(
            spotDetailContent = event.content,
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
}
