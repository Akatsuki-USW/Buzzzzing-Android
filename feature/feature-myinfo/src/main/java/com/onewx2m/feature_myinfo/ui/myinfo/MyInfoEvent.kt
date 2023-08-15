package com.onewx2m.feature_myinfo.ui.myinfo

import com.onewx2m.mvi.Event

sealed interface MyInfoEvent : Event {
    object ShowSmallLoadingLottie : MyInfoEvent
    object HideSmallLoadingLottie : MyInfoEvent
}
