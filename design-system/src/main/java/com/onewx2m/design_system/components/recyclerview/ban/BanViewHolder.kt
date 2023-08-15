package com.onewx2m.design_system.components.recyclerview.ban

import androidx.recyclerview.widget.RecyclerView
import com.onewx2m.design_system.R
import com.onewx2m.design_system.databinding.ItemRecyclerViewBanBinding

class BanViewHolder(
    private val binding: ItemRecyclerViewBanBinding,
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(data: BanItem) {
        binding.apply {
            textViewTitle.text = data.title
            textViewContent.text = data.body
            textViewDate.text = root.context.getString(R.string.ban_format, data.banStartAt, data.banEndedAt)
        }
    }
}
