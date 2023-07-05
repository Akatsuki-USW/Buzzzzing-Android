package com.onewx2m.login_signup.ui.signup.email // ktlint-disable filename

import com.onewx2m.mvi.Event

sealed interface EmailEvent : Event {
    object ChangeEmailLayoutStateNormal : EmailEvent
    object ChangeEmailLayoutStateInactive : EmailEvent
    object ChangeEmailLayoutStateSuccess : EmailEvent
    object ChangeEmailLayoutStateUnavailable : EmailEvent
}
