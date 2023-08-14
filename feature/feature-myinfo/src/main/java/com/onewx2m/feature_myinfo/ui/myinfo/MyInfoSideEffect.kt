package com.onewx2m.feature_myinfo.ui.myinfo

import com.onewx2m.mvi.SideEffect

sealed interface MyInfoSideEffect : SideEffect {
    object GoToEdit : MyInfoSideEffect
    object GoToMyArticle : MyInfoSideEffect
    object GoToNotification : MyInfoSideEffect
    object ShowOpenLicenses : MyInfoSideEffect
}
