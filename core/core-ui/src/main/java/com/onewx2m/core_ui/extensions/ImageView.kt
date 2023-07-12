package com.onewx2m.core_ui.extensions

import android.net.Uri
import android.widget.ImageView
import androidx.annotation.DrawableRes
import coil.load
import coil.transform.RoundedCornersTransformation

fun ImageView.loadProfileUri(uri: Uri?, @DrawableRes defaultDrawable: Int, radius: Int) {
    load(uri ?: defaultDrawable) {
        placeholder(defaultDrawable)
        transformations(
            RoundedCornersTransformation(
                radius.px.toFloat(),
                radius.px.toFloat(),
                radius.px.toFloat(),
                radius.px.toFloat(),
            ),
        )
    }
}

fun ImageView.loadProfileUrl(url: String, @DrawableRes defaultDrawable: Int, radius: Int) {
    load(url.ifBlank { defaultDrawable }) {
        placeholder(defaultDrawable)
        transformations(
            RoundedCornersTransformation(
                radius.px.toFloat(),
                radius.px.toFloat(),
                radius.px.toFloat(),
                radius.px.toFloat(),
            ),
        )
    }
}