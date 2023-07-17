package com.onewx2m.core_ui.extensions

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

fun RecyclerView.infiniteScrolls(method: () -> Unit) {
    addOnScrollListener(object : RecyclerView.OnScrollListener() {
        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)
            val lastVisiblePosition =
                (layoutManager as LinearLayoutManager).findLastCompletelyVisibleItemPosition()
            val isBottom = lastVisiblePosition + 1 == adapter?.itemCount
            val isDownScroll = dy > 0

            if (isBottom and isDownScroll) {
                method()
            }
        }
    })
}
