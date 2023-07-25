package com.onewx2m.core_ui.model

import kotlinx.serialization.Serializable

@Serializable
data class WriteContent(
    val title: String = "",
    val buzzzzingLocation: String = "",
    val buzzzzingLocationId: Int = -1,
    val address: String = "",
    val spotCategoryId: Int = -1,
    val imgUrls: List<String> = emptyList(),
    val content: String = "",
)
