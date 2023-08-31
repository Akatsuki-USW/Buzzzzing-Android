package com.onewx2m.design_system.components.recyclerview.notification

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.onewx2m.design_system.components.recyclerview.empty.EmptyViewHolder
import com.onewx2m.design_system.databinding.ItemRecyclerViewEmptyBinding
import com.onewx2m.design_system.databinding.ItemRecyclerViewNotificationBinding
import com.onewx2m.design_system.enum.ItemViewType

class NotificationAdapter(
    private val onItemClick: (NotificationItem) -> Unit = {},
) : ListAdapter<NotificationItem, RecyclerView.ViewHolder>(DiffCallback) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (ItemViewType.valueOf(viewType)) {
            ItemViewType.NORMAL -> {
                val binding = ItemRecyclerViewNotificationBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false,
                )
                NotificationViewHolder(binding, onItemClick)
            }

            else -> {
                val binding = ItemRecyclerViewEmptyBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false,
                )
                EmptyViewHolder(binding)
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is NotificationViewHolder) holder.bind(currentList[position])
    }

    override fun getItemViewType(position: Int): Int {
        return currentList[position].type.idx
    }

    companion object {
        val DiffCallback = object : DiffUtil.ItemCallback<NotificationItem>() {
            override fun areItemsTheSame(
                oldItem: NotificationItem,
                newItem: NotificationItem,
            ): Boolean {
                return oldItem.notificationId == newItem.notificationId
            }

            override fun areContentsTheSame(
                oldItem: NotificationItem,
                newItem: NotificationItem,
            ): Boolean {
                return oldItem == newItem
            }
        }
    }
}
