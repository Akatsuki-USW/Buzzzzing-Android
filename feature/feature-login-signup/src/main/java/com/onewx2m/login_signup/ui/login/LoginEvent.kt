package com.onewx2m.login_signup.ui.login

import com.onewx2m.mvi.Event

sealed interface LoginEvent : Event {
    object KakaoLoginButtonStateToLoading : LoginEvent
    object KakaoLoginButtonStateToEnable : LoginEvent
}
