package com.onewx2m.data.datasource

import com.onewx2m.data.model.BuzzzzingJwtEntity
import com.onewx2m.domain.Outcome
import kotlinx.coroutines.flow.Flow

interface RemoteAuthDataSource {

    suspend fun signIn(
        oauthAccessToken: String,
        socialType: String
    ) : Flow<Outcome<BuzzzzingJwtEntity>>
}