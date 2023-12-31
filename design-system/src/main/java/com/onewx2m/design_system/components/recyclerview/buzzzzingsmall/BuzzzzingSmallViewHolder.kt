package com.onewx2m.design_system.components.recyclerview.buzzzzingsmall

import androidx.core.content.ContextCompat
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
        binding.imageViewBookmark.onThrottleClick {
            locationId?.let {
                onBookmarkClick(it)
            }
        }

        binding.root.onThrottleClick {
            locationId?.let {
                onItemClick(it)
            }
        }
    }

    fun bind(data: BuzzzzingSmallItem) {
        locationId = data.id
        val (highlightColor, normalColor, backgroundResId, kor) = getColorResId(data.congestionSymbol)

        binding.apply {
            textViewLocationName.text = data.locationName
            linearLayoutCongestion.setBackgroundResource(backgroundResId)
            textViewCongestionType.text = kor
            textViewLocationType.text = data.locationCategory
            imageViewBookmark.setColorFilter(
                ContextCompat.getColor(
                    binding.root.context,
                    if (data.isBookmarked) highlightColor else normalColor,
                ),
            )
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

            Congestion.BUZZING -> CongestionAdditionalInfo(
                R.color.pink,
                R.color.pink_light,
                R.drawable.bg_solid_pink_rounded_10,
                Congestion.BUZZING.kor,
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
