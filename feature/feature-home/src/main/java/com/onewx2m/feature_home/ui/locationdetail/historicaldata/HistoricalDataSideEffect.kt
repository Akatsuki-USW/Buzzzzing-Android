package com.onewx2m.feature_home.ui.locationdetail.historicaldata

import com.onewx2m.mvi.SideEffect

sealed interface HistoricalDataSideEffect : SideEffect {
    data class ShowErrorToast(val msg: String) : HistoricalDataSideEffect
}