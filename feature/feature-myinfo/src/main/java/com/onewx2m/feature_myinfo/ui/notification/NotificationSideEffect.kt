package com.onewx2m.feature_myinfo.ui.notification

import com.onewx2m.mvi.SideEffect

sealed interface NotificationSideEffect : SideEffect {
    data class ShowErrorToast(val msg: String) : NotificationSideEffect
    object GoToLoginFragment : NotificationSideEffect
    data class GoToSpotDetailFragment(val spotId: Int) : NotificationSideEffect

    object PopBackStack : NotificationSideEffect
}
