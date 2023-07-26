package com.onewx2m.design_system.components.recyclerview.kakaolocation

import androidx.recyclerview.widget.RecyclerView
import com.onewx2m.core_ui.extensions.onThrottleClick
import com.onewx2m.design_system.databinding.ItemRecyclerViewKakaoLocationBinding

class KakaoLocationViewHolder(
    private val binding: ItemRecyclerViewKakaoLocationBinding,
    private val onItemClick: (placeName: String) -> Unit = {},
) : RecyclerView.ViewHolder(binding.root) {
    private var placeName: String? = null

    init {
        binding.constraintLayoutItem.onThrottleClick {
            placeName?.let {
                onItemClick(it)
            }
        }
    }

    fun bind(data: KakaoLocationItem) {
        placeName = data.placeName

        binding.apply {
            textViewLocationName.text = data.placeName
            textViewAddress.text =
                data.roadAddressName.ifBlank { data.addressName }
        }
    }
}
