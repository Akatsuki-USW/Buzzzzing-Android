package com.onewx2m.design_system.components.recyclerview.kakaolocation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.onewx2m.design_system.databinding.ItemRecyclerViewKakaoLocationBinding
import com.onewx2m.design_system.databinding.ItemRecyclerViewKakaoLocationLoadingBinding
import com.onewx2m.design_system.enum.ItemViewType

class KakaoLocationAdapter(
    private val onItemClick: (KakaoLocationItem) -> Unit = {},
) : ListAdapter<KakaoLocationItem, RecyclerView.ViewHolder>(DiffCallback) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (ItemViewType.valueOf(viewType)) {
            ItemViewType.NORMAL -> {
                val binding = ItemRecyclerViewKakaoLocationBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false,
                )
                KakaoLocationViewHolder(binding, onItemClick)
            }

            ItemViewType.LOADING -> {
                val binding = ItemRecyclerViewKakaoLocationLoadingBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false,
                )
                LoadingViewHolder(binding)
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return currentList[position].viewType.idx
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is KakaoLocationViewHolder) holder.bind(currentList[position])
    }

    companion object {
        val DiffCallback = object : DiffUtil.ItemCallback<KakaoLocationItem>() {
            override fun areItemsTheSame(
                oldItem: KakaoLocationItem,
                newItem: KakaoLocationItem,
            ): Boolean {
                return oldItem.addressName == newItem.addressName
            }

            override fun areContentsTheSame(
                oldItem: KakaoLocationItem,
                newItem: KakaoLocationItem,
            ): Boolean {
                return oldItem == newItem
            }
        }
    }
}
