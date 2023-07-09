package com.onewx2m.design_system.components.recyclerview.buzzzzingsmall

import androidx.recyclerview.widget.RecyclerView
import com.onewx2m.core_ui.extensions.onThrottleClick
import com.onewx2m.design_system.R
import com.onewx2m.design_system.databinding.ItemRecyclerViewBuzzzzingSmallBinding
import com.onewx2m.design_system.enum.Congestion

class BuzzzzingSmallViewHolder(
    private val binding: ItemRecyclerViewBuzzzzingSmallBinding,
    private val onItemClick: (locationId: Int) -> Unit = {},
    private val onBookmarkClick: (locationId: Int) -> Unit = {},
) : RecyclerView.ViewHolder(binding.root) {
    private var locationId: Int? = null

    init {
        locationId?.let { locationId ->
            binding.root.onThrottleClick {
                onItemClick(locationId)
            }

            binding.imageViewBookmark.onThrottleClick {
                onBookmarkClick(locationId)
            }
        }
    }

    fun bind(data: BuzzzzingSmallItem) {
        val (highlightColor, normalColor, backgroundResId, kor) = getColorResId(data.congestionSymbol)

        binding.apply {
            textViewLocationName.text = data.locationName
            linearLayoutCongestion.setBackgroundResource(backgroundResId)
            textViewCongestionType.text = kor
            textViewLocationName.text = data.locationName
            imageViewBookmark.setColorFilter(if (data.isBookmarked) highlightColor else normalColor)
        }
    }

    private fun getColorResId(congestionType: String): CongestionAdditionalInfo = try {
        when (Congestion.valueOf(congestionType)) {
            Congestion.RELAX -> CongestionAdditionalInfo(
                R.color.mint,
                R.color.mint_lignt,
                R.drawable.bg_solid_mint_rounded_10,
                Congestion.RELAX.kor,
            )

            Congestion.NORMAL -> CongestionAdditionalInfo(
                R.color.blue,
                R.color.blue_light,
                R.drawable.bg_solid_blue_rounded_10,
                Congestion.NORMAL.kor,
            )

            Congestion.CONGESTION -> CongestionAdditionalInfo(
                R.color.pink,
                R.color.pink_light,
                R.drawable.bg_solid_pink_rounded_10,
                Congestion.CONGESTION.kor,
            )
        }
    } catch (e: IllegalArgumentException) {
        CongestionAdditionalInfo(
            R.color.blue,
            R.color.blue_light,
            R.drawable.bg_solid_blue_rounded_10,
            Congestion.NORMAL.kor,
        )
    }

    data class CongestionAdditionalInfo(
        val highlightColorId: Int,
        val normalColorId: Int,
        val backgroundResId: Int,
        val kor: String,
    )
}
