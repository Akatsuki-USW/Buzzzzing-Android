package com.onewx2m.design_system.components.recyclerview.spot

import android.view.View
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import coil.dispose
import coil.load
import com.onewx2m.core_ui.extensions.loadUrl
import com.onewx2m.core_ui.extensions.onThrottleClick
import com.onewx2m.design_system.R
import com.onewx2m.design_system.databinding.ItemRecyclerViewSpotBinding
import com.onewx2m.design_system.enum.Congestion
import timber.log.Timber

class SpotViewHolder(
    private val congestionSymbol: String,
    private val binding: ItemRecyclerViewSpotBinding,
    private val onItemClick: (spotId: Int) -> Unit = {},
    private val onBookmarkClick: (spotId: Int) -> Unit = {},
) : RecyclerView.ViewHolder(binding.root) {
    private var spotId: Int? = null
    private val highlightColorId: Int
    private val normalColorId: Int

    companion object {
        private const val THUMBNAIL_RADIUS = 8
        private const val PROFILE_RADIUS = 2
    }

    init {
        val colorIds = getColorResId(congestionSymbol)
        highlightColorId = colorIds.first
        normalColorId = colorIds.second

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

    fun bind(data: SpotItem) {
        spotId = data.id

        val bookmarkColor = ContextCompat.getColor(
            binding.root.context,
            if (data.isBookmarked) highlightColorId else normalColorId,
        )

        binding.apply {
            imageViewThumbnail.visibility = View.VISIBLE

            if (data.thumbnailImageUrl == null) {
                imageViewThumbnail.visibility = View.GONE
            } else {
                imageViewThumbnail.loadUrl(
                    data.thumbnailImageUrl,
                    R.drawable.bg_solid_gray07_rounded_5,
                    THUMBNAIL_RADIUS,
                )
            }

            textViewSpotTitle.text = data.title
            imageViewBookmark.setColorFilter(bookmarkColor)
            textViewAddress.text = data.address

            if (data.userProfileImageUrl.isNotBlank()) {
                imageViewProfile.loadUrl(
                    data.userProfileImageUrl,
                    R.drawable.ic_profile,
                    PROFILE_RADIUS,
                )
            } else {
                imageViewProfile.load(R.drawable.ic_profile)
            }

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
