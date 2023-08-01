package com.onewx2m.feature_home.ui.locationdetail.historicaldata

import android.os.Bundle
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.onewx2m.design_system.components.recyclerview.dayselector.DaySelectorAdapter
import com.onewx2m.design_system.components.toast.ErrorToast
import com.onewx2m.design_system.enum.Congestion
import com.onewx2m.feature_home.databinding.FragmentHistoricalDataBinding
import com.onewx2m.mvi.MviFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HistoricalDataFragment :
    MviFragment<FragmentHistoricalDataBinding, HistoricalDataViewState, HistoricalDataEvent, HistoricalDataSideEffect, HistoricalDataViewModel>(
        FragmentHistoricalDataBinding::inflate,
    ) {
    private val congestion: String
        get() = arguments?.getString(CONGESTION) ?: ""

    private val locationId: Int
        get() = arguments?.getInt(LOCATION_ID) ?: -1

    private var doWhenInitialized: () -> Unit = {}

    companion object {
        private const val CONGESTION = "congestion"
        private const val LOCATION_ID = "locationId"

        @JvmStatic
        fun newInstance(
            congestion: String,
            locationId: Int,
            doWhenInitialized: () -> Unit,
        ): HistoricalDataFragment {
            return HistoricalDataFragment().apply {
                this.doWhenInitialized = doWhenInitialized
                arguments = Bundle().apply {
                    putString(CONGESTION, congestion)
                    putInt(LOCATION_ID, locationId)
                }
            }
        }
    }

    override val viewModel: HistoricalDataViewModel by viewModels()

    private var daySelectorAdapter: DaySelectorAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel.initData(locationId) {
            doWhenInitialized()
        }
    }

    override fun initView() {
        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext()).apply {
                orientation = LinearLayoutManager.HORIZONTAL
            }
        }
    }

    override fun render(current: HistoricalDataViewState) {
        super.render(current)

        if (daySelectorAdapter == null && current.daysItems.isNotEmpty() && current.selectedDayItem != null) {
            daySelectorAdapter = DaySelectorAdapter(Congestion.valueOf(congestion), current.daysItems, current.selectedDayItem) {
                viewModel.updateSelectedDayItem(it)
                viewModel.getBuzzzzingStatistic(locationId, it.query)
            }
        }

        if(binding.recyclerView.adapter == null && daySelectorAdapter != null) {
            binding.recyclerView.adapter = daySelectorAdapter
        }

        binding.chart.submitChartData(Congestion.valueOf(congestion), current.chartItems)
    }

    override fun handleSideEffect(sideEffect: HistoricalDataSideEffect) {
        super.handleSideEffect(sideEffect)

        when (sideEffect) {
            is HistoricalDataSideEffect.ShowErrorToast -> ErrorToast.make(binding.root, sideEffect.msg).show()
        }
    }
}
