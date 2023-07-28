package com.onewx2m.feature_bookmark.ui.bookmark.spot

import com.onewx2m.mvi.SideEffect

sealed interface BookmarkSpotSideEffect : SideEffect {
    data class ShowErrorToast(val msg: String) : BookmarkSpotSideEffect
    object GoToLoginFragment : BookmarkSpotSideEffect
}
