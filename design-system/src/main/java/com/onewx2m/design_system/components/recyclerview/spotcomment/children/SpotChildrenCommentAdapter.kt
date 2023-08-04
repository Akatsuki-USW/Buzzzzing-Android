package com.onewx2m.design_system.components.recyclerview.spotcomment.children

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.onewx2m.design_system.components.recyclerview.spotcomment.SpotCommentType
import com.onewx2m.design_system.databinding.ItemRecyclerViewChildrenCommentBinding
import com.onewx2m.design_system.databinding.ItemRecyclerViewChildrenCommentDeleteBinding

class SpotChildrenCommentAdapter(
    var currentList: List<SpotChildrenCommentItem>,
    private val onMeatBallClick: (View, SpotChildrenCommentItem) -> Unit,
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (SpotCommentType.valueOf(viewType)) {
            SpotCommentType.NORMAL -> {
                val binding = ItemRecyclerViewChildrenCommentBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false,
                )
                SpotChildrenCommentViewHolder(binding, onMeatBallClick)
            }

            else -> {
                val binding = ItemRecyclerViewChildrenCommentDeleteBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false,
                )
                SpotChildrenCommentDeleteViewHolder(binding)
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return currentList[position].type.idx
    }

    override fun getItemCount(): Int = currentList.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is SpotChildrenCommentViewHolder) holder.bind(currentList[position])
    }

    companion object {
        val DiffCallback = object : DiffUtil.ItemCallback<SpotChildrenCommentItem>() {
            override fun areItemsTheSame(
                oldItem: SpotChildrenCommentItem,
                newItem: SpotChildrenCommentItem,
            ): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(
                oldItem: SpotChildrenCommentItem,
                newItem: SpotChildrenCommentItem,
            ): Boolean {
                return oldItem == newItem
            }
        }
    }
}
