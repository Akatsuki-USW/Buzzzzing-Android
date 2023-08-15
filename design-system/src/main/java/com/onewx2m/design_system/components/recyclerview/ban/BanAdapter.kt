package com.onewx2m.design_system.components.recyclerview.ban

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.onewx2m.design_system.databinding.ItemRecyclerViewBanBinding

class BanAdapter(
) : ListAdapter<BanItem, BanViewHolder>(DiffCallback) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BanViewHolder {
        val binding = ItemRecyclerViewBanBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false,
        )
        return BanViewHolder(binding)
    }

    override fun onBindViewHolder(holder: BanViewHolder, position: Int) {
        holder.bind(currentList[position])
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
