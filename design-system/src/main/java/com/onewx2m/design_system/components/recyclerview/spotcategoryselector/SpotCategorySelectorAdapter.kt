package com.onewx2m.design_system.components.recyclerview.spotcategoryselector

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.onewx2m.design_system.databinding.ItemRecyclerViewRoundCheckboxBinding
import com.onewx2m.design_system.enum.Congestion

class SpotCategorySelectorAdapter(
    private val congestion: Congestion,
    private val dataList: List<SpotCategoryItem>,
    private var alreadySelectedData: SpotCategoryItem? = null,
    private val onItemClick: (SpotCategoryItem) -> Unit = {},
) : RecyclerView.Adapter<SpotCategorySelectorViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): SpotCategorySelectorViewHolder {
        val binding = ItemRecyclerViewRoundCheckboxBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false,
        )
        return SpotCategorySelectorViewHolder(binding, congestion) {
            onItemClick(it)
            if (it != alreadySelectedData) {
                val prevSelectedData = alreadySelectedData?.copy()
                alreadySelectedData = it.copy()
                notifyItemChanged(prevSelectedData)
            }
        }
    }

    private fun notifyItemChanged(element: SpotCategoryItem?) {
        val idx = dataList.indexOf(element)
        if (idx != -1) notifyItemChanged(idx)
    }

    override fun onBindViewHolder(holder: SpotCategorySelectorViewHolder, position: Int) {
        holder.bind(dataList[position], alreadySelectedData)
    }

    override fun getItemCount(): Int = dataList.size
}
