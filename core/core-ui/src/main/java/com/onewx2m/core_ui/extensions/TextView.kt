package com.onewx2m.core_ui.extensions

import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.text.style.StyleSpan
import android.text.style.TextAppearanceSpan
import android.widget.TextView
import androidx.annotation.StyleRes
import androidx.core.content.ContextCompat

fun TextView.changeTextColor(targetTextIds: List<Int>, colorId: Int) {
    val originalString = this.text.toString()
    val spannableString = SpannableString(originalString)

    targetTextIds.forEach { targetTextId ->
        val targetText = context.getString(targetTextId)
        val startIndex = originalString.indexOf(targetText)
        if (startIndex != -1) {
            val colorSpan = ForegroundColorSpan(ContextCompat.getColor(this.context, colorId))
            spannableString.setSpan(
                colorSpan,
                startIndex,
                startIndex + targetText.length,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE,
            )
        }
    }

    this.text = spannableString
}

fun TextView.changeTextStyle(targetTexts: List<String>, @StyleRes styleId: Int) {
    val originalString = this.text.toString()
    val spannableString = SpannableString(originalString)

    targetTexts.forEach { targetText ->
        val startIndex = originalString.indexOf(targetText)
        if (startIndex != -1) {
            val styleSpan = TextAppearanceSpan(this.context, styleId)
            spannableString.setSpan(
                styleSpan,
                startIndex,
                startIndex + targetText.length,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE,
            )
        }
    }

    this.text = spannableString
}
