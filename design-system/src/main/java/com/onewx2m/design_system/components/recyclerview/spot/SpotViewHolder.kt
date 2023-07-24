package com.onewx2m.design_system.components.recyclerview.spot

import android.view.View
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.onewx2m.core_ui.extensions.loadUrl
import com.onewx2m.core_ui.extensions.onThrottleClick
import com.onewx2m.design_system.R
import com.onewx2m.design_system.databinding.ItemRecyclerViewSpotBinding
import com.onewx2m.design_system.enum.Congestion

class SpotViewHolder(
    private val binding: ItemRecyclerViewSpotBinding,
    private val onItemClick: (spotId: Int) -> Unit = {},
    private val onBookmarkClick: (spotId: Int) -> Unit = {},
) : RecyclerView.ViewHolder(binding.root) {

    companion object {
        private const val THUMBNAIL_ROUNDED = 8
        private const val PROFILE_ROUNDED = 2
    }

    private var spotId: Int? = null

    init {
        binding.imageViewBookmark.onThrottleClick {
            spotId?.let {
                onBookmarkClick(it)
            }
        }

        binding.constraintLayoutItem.onThrottleClick {
            spotId?.let {
                onItemClick(it)
            }
        }
    }

    fun bind(congestionSymbol: String, data: SpotItem) {
        spotId = data.id
        val (highlightColorId, normalColorId) = getColorResId(congestionSymbol)

        val highlightColor = ContextCompat.getColor(binding.root.context, highlightColorId)

        val bookmarkColor = ContextCompat.getColor(
            binding.root.context,
            if (data.isBookmarked) highlightColorId else normalColorId,
        )

        binding.apply {
            data.thumbnailImageUrl?.let {
                imageViewThumbnail.loadUrl(
                    it,
                    R.drawable.bg_solid_white02_rounded_5,
                    THUMBNAIL_ROUNDED,
                )
            } ?: {
                imageViewThumbnail.visibility = View.GONE
            }

            textViewSpotTitle.text = data.title
            imageViewBookmark.setColorFilter(bookmarkColor)
            textViewAddress.text = data.address

            imageViewProfile.loadUrl(
                data.userProfileImageUrl,
                R.drawable.ic_profile,
                PROFILE_ROUNDED,
            )

            textViewNickname.text = data.userNickname
        }
    }

    private fun getColorResId(congestionType: String): Pair<Int, Int> = try {
        when (Congestion.valueOf(congestionType)) {
            Congestion.RELAX -> Pair(
                R.color.mint,
                R.color.mint_lignt,
            )

            Congestion.NORMAL -> Pair(
                R.color.blue,
                R.color.blue_light,
            )

            Congestion.BUZZING -> Pair(
                R.color.pink,
                R.color.pink_light,
            )
        }
    } catch (e: IllegalArgumentException) {
        Pair(
            R.color.blue,
            R.color.blue_light,
        )
    }
}
