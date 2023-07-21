package com.onewx2m.feature_home.ui.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.onewx2m.design_system.components.recyclerview.buzzzzingsmall.BuzzzzingSmallItem
import com.onewx2m.design_system.components.recyclerview.buzzzzingsmall.BuzzzzingSmallRecyclerView
import com.onewx2m.feature_home.databinding.ItemRecyclerViewHomeBuzzzzingSmallBinding

class HomeBuzzzzingSmallAdapter(
    private val onItemClick: (Int) -> Unit = {},
    private val onBookmarkClick: (Int) -> Unit = {},
    private val infiniteScrolls: () -> Unit = {},
) :
    RecyclerView.Adapter<HomeBuzzzzingSmallHolder>() {

    private var recyclerView: BuzzzzingSmallRecyclerView? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeBuzzzzingSmallHolder {
        val binding =
            ItemRecyclerViewHomeBuzzzzingSmallBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false,
            )

        binding.recyclerView.initAdapter(onItemClick, onBookmarkClick, infiniteScrolls)
        recyclerView = binding.recyclerView

        return HomeBuzzzzingSmallHolder(binding)
    }

    override fun onBindViewHolder(holder: HomeBuzzzzingSmallHolder, position: Int) {
    }

    override fun getItemCount(): Int = 1

    fun submitList(data: List<BuzzzzingSmallItem>) {
        recyclerView?.submitList(data)
    }
}

class HomeBuzzzzingSmallHolder(
    binding: ItemRecyclerViewHomeBuzzzzingSmallBinding,
) :
    RecyclerView.ViewHolder(binding.root)
