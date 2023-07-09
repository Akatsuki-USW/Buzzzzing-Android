package com.onewx2m.design_system.components.recyclerview.buzzzzingshort

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.onewx2m.design_system.databinding.RecyclerViewBuzzzzingShortBinding

class BuzzzzingShortAdapter(
    private val onItemClick: (locationId: Int) -> Unit = {},
    private val onBookmarkClick: (locationId: Int) -> Unit = {},
) : ListAdapter<BuzzzzingShortItem, BuzzzzingShortViewHolder>(DiffCallback) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BuzzzzingShortViewHolder {
        val binding = RecyclerViewBuzzzzingShortBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false,
        )
        return BuzzzzingShortViewHolder(binding, onItemClick, onBookmarkClick)
    }

    override fun onBindViewHolder(holder: BuzzzzingShortViewHolder, position: Int) {
        holder.bind(currentList[position])
    }

    companion object {
        val DiffCallback = object : DiffUtil.ItemCallback<BuzzzzingShortItem>() {
            override fun areItemsTheSame(
                oldItem: BuzzzzingShortItem,
                newItem: BuzzzzingShortItem,
            ): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(
                oldItem: BuzzzzingShortItem,
                newItem: BuzzzzingShortItem,
            ): Boolean {
                return oldItem == newItem
            }
        }
    }
}
