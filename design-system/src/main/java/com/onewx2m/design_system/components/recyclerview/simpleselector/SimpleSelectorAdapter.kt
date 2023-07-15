package com.onewx2m.design_system.components.recyclerview.simpleselector

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.onewx2m.design_system.databinding.ItemRecyclerViewSimpleSelectorBinding

class SimpleSelectorAdapter(
    private val alreadySelectedData: String,
    private val dataList: List<String>,
    private val onItemClick: (String) -> Unit = {},
) : RecyclerView.Adapter<SimpleSelectorViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SimpleSelectorViewHolder {
        val binding = ItemRecyclerViewSimpleSelectorBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false,
        )
        return SimpleSelectorViewHolder(binding, onItemClick)
    }

    override fun onBindViewHolder(holder: SimpleSelectorViewHolder, position: Int) {
        holder.bind(dataList[position], alreadySelectedData)
    }

    override fun getItemCount(): Int = dataList.size
}
