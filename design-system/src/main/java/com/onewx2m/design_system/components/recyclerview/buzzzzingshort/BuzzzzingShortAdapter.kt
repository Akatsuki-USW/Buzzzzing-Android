package com.onewx2m.design_system.components.recyclerview.buzzzzingshort

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.onewx2m.design_system.components.recyclerview.empty.EmptyViewHolder
import com.onewx2m.design_system.databinding.ItemRecyclerViewBuzzzzingShortBinding
import com.onewx2m.design_system.databinding.ItemRecyclerViewBuzzzzingShortLoadingBinding
import com.onewx2m.design_system.databinding.ItemRecyclerViewEmptyBinding
import com.onewx2m.design_system.databinding.ItemRecyclerViewKakaoLocationBinding
import com.onewx2m.design_system.databinding.ItemRecyclerViewKakaoLocationLoadingBinding
import com.onewx2m.design_system.enum.ItemViewType

class BuzzzzingShortAdapter(
    private val onItemClick: (BuzzzzingShortItem) -> Unit = {},
) : ListAdapter<BuzzzzingShortItem, RecyclerView.ViewHolder>(DiffCallback) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (ItemViewType.valueOf(viewType)) {
            ItemViewType.NORMAL -> {
                val binding = ItemRecyclerViewBuzzzzingShortBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false,
                )
                BuzzzzingShortViewHolder(binding, onItemClick)
            }

            ItemViewType.LOADING -> {
                val binding = ItemRecyclerViewBuzzzzingShortLoadingBinding.inflate(
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
        return currentList[position].viewType.idx
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is BuzzzzingShortViewHolder) holder.bind(currentList[position])
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
