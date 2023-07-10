package com.onewx2m.feature_home.ui.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.onewx2m.design_system.components.recyclerview.buzzzzingmedium.BuzzzzingMediumItem
import com.onewx2m.design_system.components.recyclerview.buzzzzingmedium.BuzzzzingMediumRecyclerView
import com.onewx2m.design_system.components.recyclerview.buzzzzingsmall.BuzzzzingSmallItem
import com.onewx2m.feature_home.databinding.ItemRecyclerViewHomeBuzzzzingMediumBinding

class HomeBuzzzzingMediumAdapter(
    private val onItemClick: (Int) -> Unit = {},
    private val onBookmarkClick: (Int) -> Unit = {},
    private val infiniteScrolls: () -> Unit = {},
) :
    RecyclerView.Adapter<HomeBuzzzzingMediumHolder>() {

    private var recyclerView: BuzzzzingMediumRecyclerView? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeBuzzzzingMediumHolder {
        val binding =
            ItemRecyclerViewHomeBuzzzzingMediumBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false,
            )

        binding.recyclerView.initAdapter(onItemClick, onBookmarkClick, infiniteScrolls)
        recyclerView = binding.recyclerView

        return HomeBuzzzzingMediumHolder(binding)
    }

    override fun onBindViewHolder(holder: HomeBuzzzzingMediumHolder, position: Int) {
    }

    override fun getItemCount(): Int = 1

    fun submitList(data: List<BuzzzzingMediumItem>) {
        recyclerView?.submitList(data)
    }
}

class HomeBuzzzzingMediumHolder(
    binding: ItemRecyclerViewHomeBuzzzzingMediumBinding,
) :
    RecyclerView.ViewHolder(binding.root) {
}
