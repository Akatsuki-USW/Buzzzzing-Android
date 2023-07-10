package com.onewx2m.design_system.components.recyclerview.buzzzzingmedium

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.onewx2m.design_system.databinding.ItemRecyclerViewBuzzzzingMediumBinding

class BuzzzzingMediumAdapter(
    private val onItemClick: (locationId: Int) -> Unit = {},
    private val onBookmarkClick: (locationId: Int) -> Unit = {},
) : ListAdapter<BuzzzzingMediumItem, BuzzzzingMediumViewHolder>(DiffCallback) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BuzzzzingMediumViewHolder {
        val binding = ItemRecyclerViewBuzzzzingMediumBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false,
        )
        return BuzzzzingMediumViewHolder(binding, onItemClick, onBookmarkClick)
    }

    override fun onBindViewHolder(holder: BuzzzzingMediumViewHolder, position: Int) {
        holder.bind(currentList[position])
    }

    companion object {
        val DiffCallback = object : DiffUtil.ItemCallback<BuzzzzingMediumItem>() {
            override fun areItemsTheSame(
                oldItem: BuzzzzingMediumItem,
                newItem: BuzzzzingMediumItem,
            ): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(
                oldItem: BuzzzzingMediumItem,
                newItem: BuzzzzingMediumItem,
            ): Boolean {
                return oldItem == newItem
            }
        }
    }
}
