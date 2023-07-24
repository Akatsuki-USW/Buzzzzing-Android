package com.onewx2m.design_system.components.recyclerview.spotcategoryselector

import androidx.recyclerview.widget.RecyclerView
import com.onewx2m.core_ui.extensions.onThrottleClick
import com.onewx2m.design_system.R
import com.onewx2m.design_system.databinding.ItemRecyclerViewRoundCheckboxBinding
import com.onewx2m.design_system.enum.Congestion
import timber.log.Timber


class SpotCategorySelectorViewHolder(
    private val binding: ItemRecyclerViewRoundCheckboxBinding,
    congestion: Congestion,
    private val onItemClick: (SpotCategoryItem) -> Unit = {},
) : RecyclerView.ViewHolder(binding.root) {
    private var data: SpotCategoryItem? = null

    init {
        val bgRes = when (congestion) {
            Congestion.RELAX -> R.drawable.selector_bg_empty_rounded_gray05_to_bg_solid_mint
            Congestion.NORMAL -> R.drawable.selector_bg_empty_rounded_gray05_to_bg_solid_blue
            Congestion.BUZZING -> R.drawable.selector_bg_empty_rounded_gray05_to_bg_solid_pink
        }

        binding.checkBox.setBackgroundResource(bgRes)

        binding.checkBox.onThrottleClick {
            data?.let {
                onItemClick(it)
            }
        }
    }

    fun bind(data: SpotCategoryItem, alreadySelectedData: SpotCategoryItem?) {
        this.data = data

        binding.apply {
            checkBox.text = data.name
            checkBox.isChecked = data == alreadySelectedData
        }
    }
}
