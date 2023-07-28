package com.onewx2m.feature_bookmark.ui.bookmark.buzzzzing

import com.onewx2m.design_system.components.recyclerview.buzzzzingmedium.BuzzzzingMediumItem
import com.onewx2m.mvi.ViewState

data class BookmarkBuzzzzingViewState(
    val buzzzzingMediumItem: List<BuzzzzingMediumItem> = emptyList(),
    val isInitializing: Boolean = true,
) : ViewState
