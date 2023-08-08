package com.onewx2m.core_ui.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable

const val WRITE_CONTENT_KEY = "WriteContent"

@Parcelize
@Serializable
data class WriteContent(
    val spotId: Int? = null,
    val title: String = "",
    val buzzzzingLocation: String = "",
    val buzzzzingLocationId: Int? = null,
    val address: String = "",
    val spotCategoryId: Int = -1,
    val imgUrls: List<String> = emptyList(),
    val content: String = "",
    val needDelete: Boolean = false,
) : Parcelable
