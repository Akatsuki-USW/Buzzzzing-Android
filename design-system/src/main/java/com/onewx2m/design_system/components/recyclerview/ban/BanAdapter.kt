package com.onewx2m.design_system.components.recyclerview.ban

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.onewx2m.design_system.components.recyclerview.empty.EmptyViewHolder
import com.onewx2m.design_system.databinding.ItemRecyclerViewBanBinding
import com.onewx2m.design_system.databinding.ItemRecyclerViewEmptyBinding
import com.onewx2m.design_system.enum.ItemViewType

class BanAdapter(
) : ListAdapter<BanItem, RecyclerView.ViewHolder>(DiffCallback) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (ItemViewType.valueOf(viewType)) {
            ItemViewType.NORMAL -> {
                val binding = ItemRecyclerViewBanBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false,
                )
                BanViewHolder(binding)
            }
            else -> {
                val binding = ItemRecyclerViewEmptyBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false,
                )
                EmptyViewHolder(binding)
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if(holder is BanViewHolder) holder.bind(currentList[position])
    }

    override fun getItemViewType(position: Int): Int {
        return currentList[position].type.idx
    }

    companion object {
        val DiffCallback = object : DiffUtil.ItemCallback<BanItem>() {
            override fun areItemsTheSame(
                oldItem: BanItem,
                newItem: BanItem,
            ): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(
                oldItem: BanItem,
                newItem: BanItem,
            ): Boolean {
                return oldItem == newItem
            }
        }
    }
}
