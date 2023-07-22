package com.onewx2m.design_system.components.recyclerview.dayselector

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.onewx2m.design_system.databinding.ItemRecyclerViewRoundCheckboxBinding
import com.onewx2m.design_system.enum.Congestion
import timber.log.Timber

class DaySelectorAdapter(
    private val congestion: Congestion,
    private val dataList: List<DayItem>,
    private var alreadySelectedData: DayItem? = null,
    private val onItemClick: (DayItem) -> Unit = {},
) : RecyclerView.Adapter<DaySelectorViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DaySelectorViewHolder {
        val binding = ItemRecyclerViewRoundCheckboxBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false,
        )
        return DaySelectorViewHolder(binding, congestion) {
            onItemClick(it)
            val prevSelectedData = alreadySelectedData?.copy()
            alreadySelectedData = it.copy()
            notifyItemChanged(prevSelectedData)
        }
    }

    private fun notifyItemChanged(element: DayItem?) {
        val idx = dataList.indexOf(element)
        if (idx != -1) notifyItemChanged(idx)
    }

    override fun onBindViewHolder(holder: DaySelectorViewHolder, position: Int) {
        holder.bind(dataList[position], alreadySelectedData)
    }

    override fun getItemCount(): Int = dataList.size
}
