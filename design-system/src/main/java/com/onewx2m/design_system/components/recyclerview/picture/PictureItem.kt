package com.onewx2m.design_system.components.recyclerview.picture

import android.net.Uri
import java.util.UUID

data class PictureItem(
    val uid: String = UUID.randomUUID().toString(),
    val uri: Uri? = null,
    val url: String = "",
) {
    val value
        get() = uri ?: url
}
