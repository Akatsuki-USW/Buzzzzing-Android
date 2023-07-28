package com.onewx2m.domain.repository

import com.onewx2m.domain.Outcome
import com.onewx2m.domain.model.SpotBookmark
import com.onewx2m.domain.model.SpotDetail
import com.onewx2m.domain.model.SpotList
import com.onewx2m.domain.model.UserInfo
import kotlinx.coroutines.flow.Flow
import java.io.File

interface SpotRepository {

    suspend fun getSpotOfLocationList(
        cursorId: Int,
        locationId: Int,
        categoryId: Int? = null,
    ): Flow<Outcome<SpotList>>

    suspend fun getAllSpotList(
        cursorId: Int,
        categoryId: Int? = null,
    ): Flow<Outcome<SpotList>>

    suspend fun getSpotBookmarked(
        cursorId: Int,
    ): Flow<Outcome<SpotList>>

    suspend fun spotBookmark(spotId: Int): Flow<Outcome<SpotBookmark>>

    suspend fun postSpot(
        spotCategoryId: Int,
        locationId: Int,
        title: String,
        address: String,
        content: String,
        imageFiles: List<File>,
    ): Flow<Outcome<SpotDetail>>
}
