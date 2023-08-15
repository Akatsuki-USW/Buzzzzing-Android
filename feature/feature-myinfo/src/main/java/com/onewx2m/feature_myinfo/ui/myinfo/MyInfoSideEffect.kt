package com.onewx2m.feature_myinfo.ui.myinfo

import com.onewx2m.mvi.SideEffect

sealed interface MyInfoSideEffect : SideEffect {
    object GoToEdit : MyInfoSideEffect
    object GoToMyArticle : MyInfoSideEffect
    object GoToNotification : MyInfoSideEffect
    object ShowOpenLicenses : MyInfoSideEffect
    object Logout : MyInfoSideEffect
    object Quit : MyInfoSideEffect
    object GoToBanList : MyInfoSideEffect
    data class ShowErrorToast(val msg: String) : MyInfoSideEffect
    object GoToLoginFragment : MyInfoSideEffect
    object GoToCommunityRule : MyInfoSideEffect
    object GoToTerms : MyInfoSideEffect
    object GoToPersonalInfoHandling : MyInfoSideEffect
}
