package com.onewx2m.design_system.components.recyclerview.spotcomment.parent

import android.annotation.SuppressLint
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.onewx2m.core_ui.extensions.onThrottleClick
import com.onewx2m.design_system.R
import com.onewx2m.design_system.components.recyclerview.spotcomment.children.SpotChildrenCommentAdapter
import com.onewx2m.design_system.components.recyclerview.spotcomment.children.SpotChildrenCommentItem
import com.onewx2m.design_system.databinding.ItemRecyclerViewParentCommentDeleteBinding

class SpotParentCommentDeleteViewHolder(
    private val binding: ItemRecyclerViewParentCommentDeleteBinding,
    private val onChildMeatBallClick: (View, SpotChildrenCommentItem) -> Unit,
    private val onMoreClick: (SpotParentCommentItem) -> Unit,
) : RecyclerView.ViewHolder(binding.root) {

    private var item = SpotParentCommentItem()
    private val recyclerViewAdapter =
        SpotChildrenCommentAdapter(item.visibleChildrenCommentList, onChildMeatBallClick)

    init {
        binding.recyclerViewChildren.apply {
            adapter = recyclerViewAdapter
            layoutManager = LinearLayoutManager(context)
        }

        binding.textViewMoreComment.onThrottleClick {
            onMoreClick(item)
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun bind(item: SpotParentCommentItem) = binding.apply {
        this@SpotParentCommentDeleteViewHolder.item = item

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

        recyclerViewAdapter.currentList = item.visibleChildrenCommentList
        recyclerViewAdapter.notifyDataSetChanged()
    }
}
