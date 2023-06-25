package com.onewx2m.core_ui.extensions

import android.view.View
import com.onewx2m.core_ui.util.OnThrottleClickListener

fun View.onThrottleClick(
    onClick: (v: View) -> Unit,
) {
    val listener = View.OnClickListener { onClick(it) }
    setOnClickListener(OnThrottleClickListener(listener))
}

fun View.onThrottleClick(
    interval: Long,
    onClick: (v: View) -> Unit,
) {
    val listener = View.OnClickListener { onClick(it) }
    setOnClickListener(OnThrottleClickListener(listener, interval))
}