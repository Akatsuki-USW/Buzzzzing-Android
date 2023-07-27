package com.onewx2m.design_system.components.recyclerview.kakaolocation

import androidx.recyclerview.widget.RecyclerView
import com.onewx2m.core_ui.extensions.onThrottleClick
import com.onewx2m.design_system.databinding.ItemRecyclerViewKakaoLocationBinding

class KakaoLocationViewHolder(
    private val binding: ItemRecyclerViewKakaoLocationBinding,
    private val onItemClick: (KakaoLocationItem) -> Unit = {},
) : RecyclerView.ViewHolder(binding.root) {
    private var item: KakaoLocationItem? = null

    init {
        binding.constraintLayoutItem.onThrottleClick {
            item?.let {
                onItemClick(it)
            }
        }
    }

    fun bind(data: KakaoLocationItem) {
        item = data

        binding.apply {
            textViewLocationName.text = data.placeName
            textViewAddress.text =
                data.roadAddressName.ifBlank { data.addressName }
        }
    }
}
