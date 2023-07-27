package com.onewx2m.design_system.components.recyclerview.buzzzzingshort

import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.onewx2m.core_ui.extensions.onThrottleClick
import com.onewx2m.design_system.databinding.ItemRecyclerViewBuzzzzingShortBinding
import com.onewx2m.design_system.databinding.ItemRecyclerViewKakaoLocationBinding

class BuzzzzingShortViewHolder(
    private val binding: ItemRecyclerViewBuzzzzingShortBinding,
    private val onItemClick: (BuzzzzingShortItem) -> Unit = {},
) : RecyclerView.ViewHolder(binding.root) {
    private var buzzzzingShortItem: BuzzzzingShortItem? = null

    init {
        binding.constraintLayoutItem.onThrottleClick {
            buzzzzingShortItem?.let {
                onItemClick(it)
            }
        }
    }

    fun bind(data: BuzzzzingShortItem) {
        buzzzzingShortItem = data

        binding.apply {
            textViewLocationName.text = data.name
            imageViewIcon.load(data.categoryIconUrl)
        }
    }
}
