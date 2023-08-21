package com.onewx2m.design_system.components.chart

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.annotation.ColorInt
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet
import com.onewx2m.core_ui.extensions.px
import com.onewx2m.design_system.R
import com.onewx2m.design_system.databinding.ChartCongestionBinding
import com.onewx2m.design_system.enum.Congestion
import timber.log.Timber

class CongestionChart @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
) : ConstraintLayout(context, attrs, defStyleAttr) {

    companion object {
        private const val LINE_WIDTH = 0.5f
        private const val X_LABEL_COUNT = 15
        private const val X_AXIS_MAX = 23f
        private const val X_AXIS_MIN = 9f
        private const val GRANULARITY = 1f

        private const val Y_AXIS_MAX = 3.5f
        private const val Y_AXIS_MIN = 0.5f
        private const val Y_X_OFFSET = 4f

        private const val VISIBLE_X_RANGE_MAX = 10f
        private const val EXTRA_OFFSET_BOTTOM = 4f
    }

    // 왼쪽 y축 도메인 변경
    private val yAxisLabels = listOf(
        context.getString(R.string.word_space),
        context.getString(R.string.word_relax),
        context.getString(R.string.word_normal),
        context.getString(R.string.word_buzz),
    )

    private val binding: ChartCongestionBinding

    fun submitChartData(congestion: Congestion, entries: List<ChartItem>) {
        val mainColor = getMainColor(congestion)

        val lineDataSet = mutableListOf<ILineDataSet>() // 데이터 배열 → 데이터 셋

        val chartEntry = MutableList(X_LABEL_COUNT) { Entry(it + X_AXIS_MIN, 0f) }

        entries.forEach { entry ->
            val index = chartEntry.indexOfFirst { it.x == entry.x.toFloat() }
            if (index != -1) {
                chartEntry[index] = Entry(entry.x.toFloat(), entry.y.toFloat())
            }
        }

        val drawLineDataList = mutableListOf<Entry>()
        val notDrawLineDataList = mutableListOf<Entry>()

        chartEntry.forEach {
            val (lineDataListToAddEntry, lineDataListToDraw) = if (it.y == 0f) {
                (notDrawLineDataList to drawLineDataList)
            } else {
                (drawLineDataList to notDrawLineDataList)
            }

            lineDataListToAddEntry.add(it)
            addLineData(lineDataSet, lineDataListToDraw, mainColor)
        }

        addLineData(lineDataSet, drawLineDataList, mainColor)
        addLineData(lineDataSet, notDrawLineDataList, mainColor)

        binding.chart.apply {
            data = LineData(lineDataSet)
            notifyDataSetChanged()
            invalidate()
        }
    }

    private fun addLineData(
        lineDataSet: MutableList<ILineDataSet>,
        lineDataList: MutableList<Entry>,
        @ColorInt mainColor: Int,
    ) {
        if (lineDataList.isEmpty()) return

        val set = LineDataSet(lineDataList.toList(), "").apply {
            setDrawCircleHole(false)
            lineWidth = LINE_WIDTH.px
            setCircleColor(mainColor)
            color = mainColor
            setDrawValues(false)
            highLightColor = Color.TRANSPARENT
        }
        lineDataList.clear()
        lineDataSet.add(set)
    }

    private fun getMainColor(congestion: Congestion) = when (congestion) {
        Congestion.RELAX -> ContextCompat.getColor(context, R.color.mint)
        Congestion.NORMAL -> ContextCompat.getColor(context, R.color.blue)
        Congestion.BUZZING -> ContextCompat.getColor(context, R.color.pink)
    }

    private fun initChart() = with(binding.chart) {
        xAxis.apply {
            setDrawLabels(true) // Label 표시 여부
            labelCount = X_LABEL_COUNT
            axisMaximum = X_AXIS_MAX
            axisMinimum = X_AXIS_MIN
            granularity = GRANULARITY
            isGranularityEnabled = true

            textColor = Color.BLACK
            position = XAxis.XAxisPosition.BOTTOM // x축 라벨 위치
            setDrawAxisLine(false) // Axis-Line 표시
            setDrawGridLines(false)
        }

        // 왼쪽 y축 값
        axisLeft.apply {
            axisMaximum = Y_AXIS_MAX // y축 최대값(고정)
            axisMinimum = Y_AXIS_MIN // y축 최소값(고정)
            setDrawAxisLine(false)
            setDrawGridLines(false)
            xOffset = Y_X_OFFSET.px

            valueFormatter = IndexAxisValueFormatter(yAxisLabels)
            granularity = GRANULARITY
        }

        // 오른쪽 y축 값
        axisRight.isEnabled = false

        description.isEnabled = false // 설명
        setDrawGridBackground(false)
        setBackgroundColor(Color.WHITE)
        legend.isEnabled = false
        setPinchZoom(false)
        setScaleEnabled(false)
        isDoubleTapToZoomEnabled = false
        setVisibleXRangeMaximum(VISIBLE_X_RANGE_MAX)
        setExtraOffsets(0f, 0f, 0f, EXTRA_OFFSET_BOTTOM.px)
    }

    init {
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        binding = ChartCongestionBinding.inflate(inflater, this, true)

        initChart()
    }
}
