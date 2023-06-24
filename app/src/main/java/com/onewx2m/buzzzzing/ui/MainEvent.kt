package com.onewx2m.buzzzzing.ui

import com.onewx2m.mvi.Event

sealed interface MainEvent: Event {
    object HideBottomNavigationBar: MainEvent
    object ShowBottomNavigationBar: MainEvent
}
