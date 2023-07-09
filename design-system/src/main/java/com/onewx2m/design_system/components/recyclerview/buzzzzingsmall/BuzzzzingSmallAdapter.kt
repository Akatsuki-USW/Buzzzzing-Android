package com.onewx2m.design_system.components.recyclerview.buzzzzingsmall

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.onewx2m.design_system.databinding.ItemRecyclerViewBuzzzzingSmallBinding

class BuzzzzingSmallAdapter(
    private val onItemClick: (locationId: Int) -> Unit = {},
    private val onBookmarkClick: (locationId: Int) -> Unit = {},
) : ListAdapter<BuzzzzingSmallItem, BuzzzzingSmallViewHolder>(DiffCallback) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BuzzzzingSmallViewHolder {
        val binding = ItemRecyclerViewBuzzzzingSmallBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false,
        )
        return BuzzzzingSmallViewHolder(binding, onItemClick, onBookmarkClick)
    }

    override fun onBindViewHolder(holder: BuzzzzingSmallViewHolder, position: Int) {
        holder.bind(currentList[position])
    }

    companion object {
        val DiffCallback = object : DiffUtil.ItemCallback<BuzzzzingSmallItem>() {
            override fun areItemsTheSame(
                oldItem: BuzzzzingSmallItem,
                newItem: BuzzzzingSmallItem,
            ): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(
                oldItem: BuzzzzingSmallItem,
                newItem: BuzzzzingSmallItem,
            ): Boolean {
                return oldItem == newItem
            }
        }
    }
}
