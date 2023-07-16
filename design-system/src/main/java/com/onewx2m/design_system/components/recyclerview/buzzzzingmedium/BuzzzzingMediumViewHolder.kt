package com.onewx2m.design_system.components.recyclerview.buzzzzingmedium

import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.onewx2m.core_ui.extensions.onThrottleClick
import com.onewx2m.design_system.R
import com.onewx2m.design_system.databinding.ItemRecyclerViewBuzzzzingMediumBinding
import com.onewx2m.design_system.enum.Congestion

class BuzzzzingMediumViewHolder(
    private val binding: ItemRecyclerViewBuzzzzingMediumBinding,
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

        binding.constraintLayoutItem.onThrottleClick {
            locationId?.let {
                onItemClick(it)
            }
        }
    }

    fun bind(data: BuzzzzingMediumItem) {
        locationId = data.id
        val (highlightColorId, normalColorId, kor) = getColorResId(data.congestionSymbol)

        val highlightColor = ContextCompat.getColor(binding.root.context, highlightColorId)

        val bookmarkColor = ContextCompat.getColor(
            binding.root.context,
            if (data.isBookmarked) highlightColorId else normalColorId,
        )

        binding.apply {
            textViewLocationName.text = data.locationName
            textViewCongestion.setTextColor(highlightColor)
            textViewCongestion.text = binding.root.context.getString(R.string.word_tag, kor)
            imageViewBookmark.setColorFilter(bookmarkColor)
            imageViewIcon.load(data.iconUrl)
        }
    }

    private fun getColorResId(congestionType: String): Triple<Int, Int, String> = try {
        when (Congestion.valueOf(congestionType)) {
            Congestion.RELAX -> Triple(
                R.color.mint,
                R.color.mint_lignt,
                Congestion.RELAX.kor,
            )

            Congestion.NORMAL -> Triple(
                R.color.blue,
                R.color.blue_light,
                Congestion.NORMAL.kor,
            )

            Congestion.BUZZING -> Triple(
                R.color.pink,
                R.color.pink_light,
                Congestion.BUZZING.kor,
            )
        }
    } catch (e: IllegalArgumentException) {
        Triple(
            R.color.blue,
            R.color.blue_light,
            Congestion.NORMAL.kor,
        )
    }
}
