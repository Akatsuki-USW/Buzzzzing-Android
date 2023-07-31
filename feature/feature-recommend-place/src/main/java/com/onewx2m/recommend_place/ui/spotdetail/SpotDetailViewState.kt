package com.onewx2m.recommend_place.ui.spotdetail

import com.onewx2m.mvi.ViewState
import com.onewx2m.recommend_place.ui.spotdetail.adapter.SpotDetailContentItem

data class SpotDetailViewState(
    val spotDetailContent: SpotDetailContentItem = SpotDetailContentItem(),
    val isLoadingLottieVisible: Boolean = true,
    val isRecyclerViewVisible: Boolean = false,
) : ViewState
