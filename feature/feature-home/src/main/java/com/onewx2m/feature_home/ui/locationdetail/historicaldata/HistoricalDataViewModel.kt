package com.onewx2m.feature_home.ui.locationdetail.historicaldata

import androidx.lifecycle.viewModelScope
import com.onewx2m.design_system.components.chart.ChartItem
import com.onewx2m.design_system.components.recyclerview.dayselector.DayItem
import com.onewx2m.domain.Outcome
import com.onewx2m.domain.collectOutcome
import com.onewx2m.domain.exception.common.CommonException
import com.onewx2m.domain.model.BuzzzzingStatistics
import com.onewx2m.domain.usecase.GetBuzzzzingStatisticUseCase
import com.onewx2m.domain.usecase.GetCongestionHistoricalDateCategoryUseCase
import com.onewx2m.mvi.MviViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class HistoricalDataViewModel @Inject constructor(
    private val getBuzzzingStatisticUseCase: GetBuzzzzingStatisticUseCase,
    private val getCongestionHistoricalDateCategoryUseCase: GetCongestionHistoricalDateCategoryUseCase,
) :
    MviViewModel<HistoricalDataViewState, HistoricalDataEvent, HistoricalDataSideEffect>(
        HistoricalDataViewState(),
    ) {

    private val lastWeekDate = LocalDate.now().minusDays(7).toString()

    fun initData(locationId: Int, onSuccess: () -> Unit) = viewModelScope.launch {
        initDaysItems().join()
        getBuzzzzingStatistic(locationId, lastWeekDate).join()
        onSuccess()
    }

    private fun initDaysItems() = viewModelScope.launch(IO) {
        val daysItems = getCongestionHistoricalDateCategoryUseCase().first()
            .map { DayItem(key = it.key, value = it.value, query = it.query) }
        val todayItem = daysItems.find { it.query == lastWeekDate } ?: daysItems[0]

        postEvent(HistoricalDataEvent.UpdateDaysItems(daysItems))
        postEvent(HistoricalDataEvent.UpdateSelectedDayItem(todayItem))
    }

    fun getBuzzzzingStatistic(locationId: Int, date: String) = viewModelScope.launch {
        getBuzzzingStatisticUseCase(
            locationId,
            date,
        ).collectOutcome(::handleBuzzzingStatisticSuccess, ::handleBuzzzingStatisticError)
    }

    private fun handleBuzzzingStatisticSuccess(outcome: Outcome.Success<BuzzzzingStatistics>) {
        postEvent(
            HistoricalDataEvent.UpdateChartItems(
                outcome.data.content.map {
                    ChartItem(
                        it.time,
                        it.congestionLevel,
                    )
                },
            ),
        )
    }

    fun updateSelectedDayItem(dayItem: DayItem) {
        postEvent(HistoricalDataEvent.UpdateSelectedDayItem(dayItem))
    }

    private fun handleBuzzzingStatisticError(outcome: Outcome.Failure<BuzzzzingStatistics>) {
        postSideEffect(
            HistoricalDataSideEffect.ShowErrorToast(
                outcome.error?.message ?: CommonException.UnknownException().snackBarMessage,
            ),
        )
    }

    override fun reduceState(
        current: HistoricalDataViewState,
        event: HistoricalDataEvent,
    ): HistoricalDataViewState = when (event) {
        is HistoricalDataEvent.UpdateChartItems -> current.copy(chartItems = event.chartItems)
        is HistoricalDataEvent.UpdateDaysItems -> current.copy(daysItems = event.daysItems)
        is HistoricalDataEvent.UpdateSelectedDayItem -> current.copy(selectedDayItem = event.selectedDayItem)
    }
}
