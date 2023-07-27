package com.onewx2m.design_system.components.recyclerview.picture

import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.onewx2m.core_ui.extensions.onThrottleClick
import com.onewx2m.design_system.R
import com.onewx2m.design_system.databinding.ItemRecyclerViewBuzzzzingSmallBinding
import com.onewx2m.design_system.databinding.ItemRecyclerViewPictureBinding
import com.onewx2m.design_system.enum.Congestion

class PictureViewHolder(
    private val binding: ItemRecyclerViewPictureBinding,
    private val onCloseClick: (PictureItem) -> Unit = {},
) : RecyclerView.ViewHolder(binding.root) {
    private var pictureItem: PictureItem? = null

    init {
        binding.imageViewClose.onThrottleClick {
            pictureItem?.let {
                onCloseClick(it)
            }
        }
    }

    fun bind(data: PictureItem) {
        pictureItem = data

        binding.imageViewPicture.load(data.value)
    }
}
