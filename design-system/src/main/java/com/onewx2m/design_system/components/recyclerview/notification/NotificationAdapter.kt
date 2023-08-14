package com.onewx2m.design_system.components.recyclerview.notification

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.onewx2m.design_system.databinding.ItemRecyclerViewNotificationBinding

class NotificationAdapter(
    private val onItemClick: (NotificationItem) -> Unit = {},
) : ListAdapter<NotificationItem, NotificationViewHolder>(DiffCallback) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotificationViewHolder {
        val binding = ItemRecyclerViewNotificationBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false,
        )
        return NotificationViewHolder(binding, onItemClick)
    }

    override fun onBindViewHolder(holder: NotificationViewHolder, position: Int) {
        holder.bind(currentList[position])
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
