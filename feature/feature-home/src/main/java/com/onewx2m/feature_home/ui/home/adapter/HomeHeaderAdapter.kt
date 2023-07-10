package com.onewx2m.feature_home.ui.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.onewx2m.core_ui.extensions.changeTextColor
import com.onewx2m.feature_home.R
import com.onewx2m.feature_home.databinding.ItemRecyclerViewHomeHeaderBinding

class HomeHeaderAdapter() :
    RecyclerView.Adapter<HomeHeaderHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeHeaderHolder {
        val binding =
            ItemRecyclerViewHomeHeaderBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false,
            )
        return HomeHeaderHolder(binding)
    }

    override fun onBindViewHolder(holder: HomeHeaderHolder, position: Int) {
    }

    override fun getItemCount(): Int = 1
}

class HomeHeaderHolder(private val binding: ItemRecyclerViewHomeHeaderBinding) :
    RecyclerView.ViewHolder(binding.root) {

    init {
        binding.textViewTitle.changeTextColor(
            listOf(R.string.home_title_highlight),
            com.onewx2m.design_system.R.color.mint,
        )
    }
}
