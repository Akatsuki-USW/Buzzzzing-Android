package com.onewx2m.feature_home.ui.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.onewx2m.design_system.components.recyclerview.buzzzzingsmall.BuzzzzingSmallItem
import com.onewx2m.feature_home.databinding.ItemRecyclerViewHomeBuzzzzingSmallBinding

class HomeBuzzzzingSmallAdapter(
    private val onItemClick: (Int) -> Unit = {},
    private val onBookmarkClick: (Int) -> Unit = {},
    private val infiniteScrolls: () -> Unit = {},
) :
    RecyclerView.Adapter<HomeBuzzzzingSmallHolder>() {

    private var currentList: List<BuzzzzingSmallItem> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeBuzzzzingSmallHolder {
        val binding =
            ItemRecyclerViewHomeBuzzzzingSmallBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false,
            )

        return HomeBuzzzzingSmallHolder(binding)
    }

    override fun onBindViewHolder(holder: HomeBuzzzzingSmallHolder, position: Int) {
        holder.bind(currentList, onItemClick, onBookmarkClick, infiniteScrolls)
    }

    override fun getItemCount(): Int = 1

    fun setData(data: List<BuzzzzingSmallItem>) {
        currentList = data.toList()
        notifyItemChanged(0)
    }
}

class HomeBuzzzzingSmallHolder(
    private val binding: ItemRecyclerViewHomeBuzzzzingSmallBinding,
) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(
        data: List<BuzzzzingSmallItem>,
        onItemClick: (Int) -> Unit = {},
        onBookmarkClick: (Int) -> Unit = {},
        infiniteScrolls: () -> Unit = {},
    ) {
        binding.recyclerView.initAdapter(onItemClick, onBookmarkClick, infiniteScrolls)
        binding.recyclerView.submitList(data)
    }
}
