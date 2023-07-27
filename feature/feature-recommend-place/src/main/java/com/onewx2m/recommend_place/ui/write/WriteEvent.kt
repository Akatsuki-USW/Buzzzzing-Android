package com.onewx2m.recommend_place.ui.write

import com.onewx2m.mvi.Event

sealed interface WriteEvent : Event {
    data class ChangeKakaoLocationInputLayout(
        val kakaoLocation: String,
    ) : WriteEvent

    data class ChangeBuzzzzingLocationInputLayout(
        val buzzzzingLocation: String,
    ) : WriteEvent
}
