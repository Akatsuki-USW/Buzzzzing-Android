package com.onewx2m.domain.repository

import com.onewx2m.domain.Outcome
import com.onewx2m.domain.model.SpotBookmark
import com.onewx2m.domain.model.SpotList
import kotlinx.coroutines.flow.Flow

interface SpotRepository {

    suspend fun getSpotOfLocationList(
        cursorId: Int,
        locationId: Int,
        categoryId: Int? = null,
    ): Flow<Outcome<SpotList>>

    suspend fun spotBookmark(spotId: Int): Flow<Outcome<SpotBookmark>>
}
