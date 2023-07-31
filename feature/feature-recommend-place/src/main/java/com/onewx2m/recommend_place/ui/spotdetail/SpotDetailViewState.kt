package com.onewx2m.recommend_place.ui.spotdetail

import com.onewx2m.mvi.ViewState

data class SpotDetailViewState(
    val isLoadingLottieVisible: Boolean = true,
    val isRecyclerViewVisible: Boolean = false,
) : ViewState
