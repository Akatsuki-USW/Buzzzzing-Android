package com.onewx2m.core_ui.extensions

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

private const val LAST_ITEM_OFFSET = 5

fun RecyclerView.infiniteScrolls(method: () -> Unit) {
    addOnScrollListener(object : RecyclerView.OnScrollListener() {
        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)
            val lastVisiblePosition =
                (layoutManager as LinearLayoutManager).findLastCompletelyVisibleItemPosition()
            val isBottom = lastVisiblePosition + LAST_ITEM_OFFSET >= (adapter?.itemCount ?: 0)
            val isDownScroll = dy > 0

            if (isBottom and isDownScroll) {
                method()
            }
        }
    })
}
