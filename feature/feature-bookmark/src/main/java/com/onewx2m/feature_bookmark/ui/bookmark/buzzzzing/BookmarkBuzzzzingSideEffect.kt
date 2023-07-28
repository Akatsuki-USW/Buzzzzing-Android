package com.onewx2m.feature_bookmark.ui.bookmark.buzzzzing

import com.onewx2m.mvi.SideEffect

sealed interface BookmarkBuzzzzingSideEffect : SideEffect {
    data class ShowErrorToast(val msg: String) : BookmarkBuzzzzingSideEffect
    object GoToLoginFragment : BookmarkBuzzzzingSideEffect
    data class GoToLocationDetailFragment(val locationId: Int) : BookmarkBuzzzzingSideEffect
}