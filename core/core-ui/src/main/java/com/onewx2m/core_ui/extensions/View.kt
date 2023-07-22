package com.onewx2m.core_ui.extensions

import android.view.View
import android.view.animation.AlphaAnimation
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

fun View.setVisibleWithAnimation(animationDuration: Long = 250L) {
    val visibleAnimation = AlphaAnimation(0f, 1f)
    visibleAnimation.duration = animationDuration
    visibility = View.VISIBLE
    animation = visibleAnimation
}

fun View.setGoneWithAnimation(animationDuration: Long = 250L) {
    val invisibleAnimation = AlphaAnimation(1f, 0f)
    invisibleAnimation.duration = animationDuration
    visibility = View.GONE
    animation = invisibleAnimation
}