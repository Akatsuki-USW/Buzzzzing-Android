package com.onewx2m.design_system.components.recyclerview.spotcomment.children

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.onewx2m.core_ui.extensions.loadUrl
import com.onewx2m.core_ui.extensions.onThrottleClick
import com.onewx2m.design_system.R
import com.onewx2m.design_system.databinding.ItemRecyclerViewChildrenCommentBinding

class SpotChildrenCommentViewHolder(
    private val binding: ItemRecyclerViewChildrenCommentBinding,
    private val onMeatBallClick: (View, SpotChildrenCommentItem) -> Unit,
) : RecyclerView.ViewHolder(binding.root) {

    companion object {
        private const val PROFILE_RADIUS = 6
    }

    private var item = SpotChildrenCommentItem()

    init {
        binding.imageViewMeatBall.onThrottleClick {
            onMeatBallClick(it, item)
        }
    }

    fun bind(item: SpotChildrenCommentItem) {
        this.item = item

        binding.apply {
            if (item.profileImageUrl.isNotBlank()) {
                imageViewProfile.loadUrl(
                    item.profileImageUrl,
                    R.drawable.ic_profile,
                    PROFILE_RADIUS,
                )
            } else {
                imageViewProfile.load(R.drawable.ic_profile)
            }

            textViewNickname.text = item.nickname
            textViewCreatedAt.text = item.createdAt
            textViewContent.text = item.content
        }
    }
}
