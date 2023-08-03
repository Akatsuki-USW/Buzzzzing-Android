package com.onewx2m.design_system.components.recyclerview.spotcomment.parent

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.onewx2m.design_system.components.recyclerview.spotcomment.SpotCommentType
import com.onewx2m.design_system.components.recyclerview.spotcomment.children.SpotChildrenCommentItem
import com.onewx2m.design_system.databinding.ItemRecyclerViewParentCommentBinding
import com.onewx2m.design_system.databinding.ItemRecyclerViewParentCommentDeleteBinding
import com.onewx2m.design_system.databinding.ItemRecyclerViewSpotCommentLoadingBinding

class SpotParentCommentAdapter(
    private val onParentMeatBallClick: (View, SpotParentCommentItem) -> Unit,
    private val onChildMeatBallClick: (View, SpotChildrenCommentItem) -> Unit,
) :
    ListAdapter<SpotParentCommentItem, RecyclerView.ViewHolder>(DiffCallback) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (SpotCommentType.valueOf(viewType)) {
            SpotCommentType.NORMAL -> {
                val binding = ItemRecyclerViewParentCommentBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false,
                )
                SpotParentCommentViewHolder(binding, onParentMeatBallClick, onChildMeatBallClick)
            }

            SpotCommentType.DELETE -> {
                val binding = ItemRecyclerViewParentCommentDeleteBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false,
                )
                SpotParentCommentDeleteViewHolder(binding)
            }

            SpotCommentType.LOADING -> {
                val binding = ItemRecyclerViewSpotCommentLoadingBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false,
                )
                LoadingViewHolder(binding)
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return currentList[position].type.idx
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is SpotParentCommentViewHolder) holder.bind(currentList[position])
    }

    companion object {
        val DiffCallback = object : DiffUtil.ItemCallback<SpotParentCommentItem>() {
            override fun areItemsTheSame(
                oldItem: SpotParentCommentItem,
                newItem: SpotParentCommentItem,
            ): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(
                oldItem: SpotParentCommentItem,
                newItem: SpotParentCommentItem,
            ): Boolean {
                return oldItem == newItem
            }
        }
    }
}
