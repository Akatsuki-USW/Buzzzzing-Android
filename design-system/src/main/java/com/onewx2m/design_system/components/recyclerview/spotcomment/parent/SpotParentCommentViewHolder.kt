package com.onewx2m.design_system.components.recyclerview.spotcomment.parent

import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.onewx2m.core_ui.extensions.loadUrl
import com.onewx2m.core_ui.extensions.onThrottleClick
import com.onewx2m.design_system.R
import com.onewx2m.design_system.components.recyclerview.spotcomment.children.SpotChildrenCommentAdapter
import com.onewx2m.design_system.components.recyclerview.spotcomment.children.SpotChildrenCommentItem
import com.onewx2m.design_system.databinding.ItemRecyclerViewParentCommentBinding

class SpotParentCommentViewHolder(
    private val binding: ItemRecyclerViewParentCommentBinding,
    private val onParentMeatBallClick: (View, SpotParentCommentItem) -> Unit,
    private val onChildMeatBallClick: (View, SpotChildrenCommentItem) -> Unit,
    private val onMoreClick: (SpotParentCommentItem) -> Unit,
) : RecyclerView.ViewHolder(binding.root) {

    companion object {
        private const val PROFILE_RADIUS = 6
    }

    private var item = SpotParentCommentItem()
    private val listAdapter = SpotChildrenCommentAdapter(onChildMeatBallClick)

    init {
        binding.imageViewMeatBall.onThrottleClick {
            onParentMeatBallClick(it, item)
        }

        binding.recyclerViewChildren.apply {
            adapter = listAdapter
            itemAnimator = null
            layoutManager = LinearLayoutManager(context)
        }

        binding.textViewMoreComment.onThrottleClick {
            onMoreClick(item)
        }
    }

    fun bind(item: SpotParentCommentItem) {
        this.item = item

        binding.apply {
            if (item.profileImageUrl.isNotBlank()) {
                imageViewProfile.loadUrl(
                    item.profileImageUrl,
                    R.drawable.bg_solid_gray07_rounded_5,
                    PROFILE_RADIUS,
                )
            }

            textViewNickname.text = item.nickname
            textViewCreatedAt.text = item.createdAt
            textViewContent.text = item.content

            if (item.totalChildrenCount > item.visibleChildrenCommentList.size) {
                val moreCount = item.totalChildrenCount - item.visibleChildrenCommentList.size
                binding.textViewMoreComment.text = binding.root.context.getString(
                    R.string.item_recycler_view_parent_comment_more_children,
                    moreCount,
                )
                binding.textViewMoreComment.visibility = View.VISIBLE
            } else {
                binding.textViewMoreComment.visibility = View.GONE
            }

            listAdapter.submitList(item.visibleChildrenCommentList)
        }
    }
}