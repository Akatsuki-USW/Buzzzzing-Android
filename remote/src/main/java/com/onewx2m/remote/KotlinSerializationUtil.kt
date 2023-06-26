package com.onewx2m.remote

import kotlinx.serialization.json.Json

object KotlinSerializationUtil {
    val json = Json { ignoreUnknownKeys = true }
}