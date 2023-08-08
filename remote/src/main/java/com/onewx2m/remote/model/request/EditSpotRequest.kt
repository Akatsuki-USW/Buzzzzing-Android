package com.onewx2m.remote.model.request

import kotlinx.serialization.Serializable

@Serializable
data class EditSpotRequest(
    val title: String,
    val address: String,
    val content: String,
    val imageUrls: List<String>,
    val locationId: Int,
    val spotCategoryId: Int,
)
