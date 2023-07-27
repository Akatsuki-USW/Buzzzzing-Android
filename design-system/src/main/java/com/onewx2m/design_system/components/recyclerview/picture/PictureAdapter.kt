package com.onewx2m.design_system.components.recyclerview.picture

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.onewx2m.design_system.databinding.ItemRecyclerViewPictureBinding

class PictureAdapter(
    private val onCloseClick: (PictureItem) -> Unit = {},
) : ListAdapter<PictureItem, PictureViewHolder>(DiffCallback) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PictureViewHolder {
        val binding = ItemRecyclerViewPictureBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false,
        )
        return PictureViewHolder(binding, onCloseClick)
    }

    override fun onBindViewHolder(holder: PictureViewHolder, position: Int) {
        holder.bind(currentList[position])
    }

    companion object {
        val DiffCallback = object : DiffUtil.ItemCallback<PictureItem>() {
            override fun areItemsTheSame(
                oldItem: PictureItem,
                newItem: PictureItem,
            ): Boolean {
                return oldItem.uid == newItem.uid
            }

            override fun areContentsTheSame(
                oldItem: PictureItem,
                newItem: PictureItem,
            ): Boolean {
                return oldItem == newItem
            }
        }
    }
}
