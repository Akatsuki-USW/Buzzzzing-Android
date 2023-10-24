package com.onewx2m.login_signup.ui.signup

import com.onewx2m.design_system.components.button.MainButtonType
import com.onewx2m.mvi.Event

sealed interface SignUpEvent : Event {
    data class ChangeMainButtonState(val mainButtonType: MainButtonType) : SignUpEvent
    data class ChangeViewPagerPosition(val position: Int) : SignUpEvent
    object HideViewPagerAndShowLottie : SignUpEvent
    object ShowViewPagerAndHideLottie : SignUpEvent
}
