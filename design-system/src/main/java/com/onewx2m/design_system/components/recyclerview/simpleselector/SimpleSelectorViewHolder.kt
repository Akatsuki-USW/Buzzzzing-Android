package com.onewx2m.design_system.components.recyclerview.simpleselector

import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.onewx2m.core_ui.extensions.onThrottleClick
import com.onewx2m.design_system.R
import com.onewx2m.design_system.databinding.ItemRecyclerViewSimpleSelectorBinding

class SimpleSelectorViewHolder(
    private val binding: ItemRecyclerViewSimpleSelectorBinding,
    private val onItemClick: (String) -> Unit = {},
) : RecyclerView.ViewHolder(binding.root) {
    private var data: String? = null

    init {

        binding.root.onThrottleClick {
            data?.let {
                onItemClick(it)
            }
        }
    }

    fun bind(data: String, alreadySelectedData: String) {
        this.data = data

        binding.apply {
            textView.text = data
            textView.setTextColor(
                root.context.getColor(
                    if (data == alreadySelectedData) R.color.blue else R.color.gray03,
                ),
            )
            imageViewSelect.isVisible = data == alreadySelectedData
        }
    }
}
