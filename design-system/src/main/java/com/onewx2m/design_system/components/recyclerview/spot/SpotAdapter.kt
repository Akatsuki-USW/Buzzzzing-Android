package com.onewx2m.design_system.components.recyclerview.spot

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.onewx2m.design_system.components.recyclerview.empty.EmptyViewHolder
import com.onewx2m.design_system.databinding.ItemRecyclerViewEmptyBinding
import com.onewx2m.design_system.databinding.ItemRecyclerViewSpotBinding
import com.onewx2m.design_system.databinding.ItemRecyclerViewSpotLoadingBinding
import com.onewx2m.design_system.enum.ItemViewType

class SpotAdapter(
    private val congestion: String,
    private val onItemClick: (spotId: Int) -> Unit = {},
    private val onBookmarkClick: (spotId: Int) -> Unit = {},
) : ListAdapter<SpotItem, RecyclerView.ViewHolder>(DiffCallback) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (ItemViewType.valueOf(viewType)) {
            ItemViewType.NORMAL -> {
                val binding = ItemRecyclerViewSpotBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false,
                )
                SpotViewHolder(congestion, binding, onItemClick, onBookmarkClick)
            }
            ItemViewType.LOADING -> {
                val binding = ItemRecyclerViewSpotLoadingBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false,
                )
                LoadingViewHolder(binding)
            }
            ItemViewType.EMPTY -> {
                val binding = ItemRecyclerViewEmptyBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false,
                )
                EmptyViewHolder(binding)
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return currentList[position].type.idx
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is SpotViewHolder) holder.bind(currentList[position])
    }

    companion object {
        val DiffCallback = object : DiffUtil.ItemCallback<SpotItem>() {
            override fun areItemsTheSame(
                oldItem: SpotItem,
                newItem: SpotItem,
            ): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(
                oldItem: SpotItem,
                newItem: SpotItem,
            ): Boolean {
                return oldItem == newItem
            }
        }
    }
}
