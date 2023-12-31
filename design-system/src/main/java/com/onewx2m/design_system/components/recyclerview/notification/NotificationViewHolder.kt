package com.onewx2m.design_system.components.recyclerview.notification

import androidx.recyclerview.widget.RecyclerView
import com.onewx2m.core_ui.extensions.onThrottleClick
import com.onewx2m.design_system.R
import com.onewx2m.design_system.databinding.ItemRecyclerViewNotificationBinding

class NotificationViewHolder(
    private val binding: ItemRecyclerViewNotificationBinding,
    private val onItemClick: (NotificationItem) -> Unit = {},
) : RecyclerView.ViewHolder(binding.root) {
    private var item: NotificationItem? = null

    init {
        binding.constraintLayoutItem.onThrottleClick {
            item?.let {
                onItemClick(it)
            }
        }
    }

    fun bind(data: NotificationItem) {
        item = data

        binding.apply {
            constraintLayoutItem.setBackgroundResource(if (data.isRead) R.drawable.ripple_gray03_bg_solid_gray07 else R.drawable.ripple_gray03_bg_solid_white01)
            textViewNotificationTitle.text = data.title
            textViewNotificationContent.text = data.body
            textViewNotificationDate.text = data.createdAt
        }
    }
}
