package com.onewx2m.design_system.components.recyclerview.buzzzzingmedium

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.onewx2m.design_system.databinding.ItemRecyclerViewBuzzzzingMediumBinding
import com.onewx2m.design_system.databinding.ItemRecyclerViewBuzzzzingMediumLoadingBinding
import com.onewx2m.design_system.enum.ItemViewType

class BuzzzzingMediumAdapter(
    private val onItemClick: (locationId: Int) -> Unit = {},
    private val onBookmarkClick: (locationId: Int) -> Unit = {},
) : ListAdapter<BuzzzzingMediumItem, RecyclerView.ViewHolder>(DiffCallback) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (ItemViewType.valueOf(viewType)) {
            ItemViewType.NORMAL -> {
                val binding = ItemRecyclerViewBuzzzzingMediumBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false,
                )
                BuzzzzingMediumViewHolder(binding, onItemClick, onBookmarkClick)
            }
            ItemViewType.LOADING -> {
                val binding = ItemRecyclerViewBuzzzzingMediumLoadingBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false,
                )
                LoadingViewHolder(binding)
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return currentList[position].type.idx
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if(holder is BuzzzzingMediumViewHolder) holder.bind(currentList[position])
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
