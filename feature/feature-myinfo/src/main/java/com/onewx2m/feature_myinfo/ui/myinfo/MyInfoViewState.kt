package com.onewx2m.feature_myinfo.ui.myinfo

import com.onewx2m.mvi.ViewState

sealed interface MyInfoViewState : ViewState {
    object Default : MyInfoViewState
}
