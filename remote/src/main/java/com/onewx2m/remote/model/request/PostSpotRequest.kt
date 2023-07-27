package com.onewx2m.remote.model.request

import kotlinx.serialization.Serializable

@Serializable
data class PostSpotRequest(
    val title: String,
    val address: String,
    val content: String,
    val imageUrls: List<String>
)
