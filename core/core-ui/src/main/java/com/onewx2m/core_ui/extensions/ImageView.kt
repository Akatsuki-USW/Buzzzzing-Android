package com.onewx2m.core_ui.extensions

import android.net.Uri
import android.widget.ImageView
import androidx.annotation.DrawableRes
import coil.ImageLoader
import coil.load
import coil.request.CachePolicy
import coil.transform.RoundedCornersTransformation

fun ImageView.loadUri(uri: Uri?, @DrawableRes defaultDrawable: Int, radius: Int) {
    load(uri ?: defaultDrawable) {
        placeholder(defaultDrawable)
        crossfade(true)
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

fun ImageView.loadUrl(url: String, @DrawableRes defaultDrawable: Int, radius: Int) {
    load(url.ifBlank { defaultDrawable }) {
        placeholder(defaultDrawable)
        crossfade(true)
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