package com.onewx2m.feature_home.ui.locationdetail.historicaldata

import com.onewx2m.design_system.components.chart.ChartItem
import com.onewx2m.design_system.components.recyclerview.dayselector.DayItem
import com.onewx2m.mvi.ViewState

data class HistoricalDataViewState(
    val chartItems: List<ChartItem> = emptyList(),
    val daysItems: List<DayItem> = emptyList(),
    val selectedDayItem: DayItem? = null
) : ViewState
