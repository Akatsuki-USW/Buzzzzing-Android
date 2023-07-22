package com.onewx2m.feature_home.ui.locationdetail.historicaldata

import com.onewx2m.design_system.components.chart.ChartItem
import com.onewx2m.design_system.components.recyclerview.dayselector.DayItem
import com.onewx2m.mvi.Event

sealed interface HistoricalDataEvent : Event {
    data class UpdateChartItems(val chartItems: List<ChartItem>) : HistoricalDataEvent
    data class UpdateDaysItems(
        val daysItems: List<DayItem>,
    ) : HistoricalDataEvent
    data class UpdateSelectedDayItem(
        val selectedDayItem: DayItem?,
    ) : HistoricalDataEvent
}
