package com.onewx2m.core_ui.extensions

import android.net.Uri
import android.widget.ImageView
import androidx.annotation.DrawableRes
import coil.load
import coil.transform.RoundedCornersTransformation

fun ImageView.loadProfileUri(uri: Uri?, @DrawableRes defaultDrawable: Int) {
    load(uri ?: defaultDrawable) {
        transformations(
            RoundedCornersTransformation(
                20.px.toFloat(),
                20.px.toFloat(),
                20.px.toFloat(),
                20.px.toFloat(),
            ),
        )
    }
}