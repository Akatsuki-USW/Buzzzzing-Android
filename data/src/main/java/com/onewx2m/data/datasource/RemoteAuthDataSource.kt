package com.onewx2m.data.datasource

import com.onewx2m.data.model.JwtEntity
import com.onewx2m.domain.Outcome
import kotlinx.coroutines.flow.Flow

interface RemoteAuthDataSource {

    suspend fun loginByKakao(
        oauthAccessToken: String
    ) : Flow<Outcome<JwtEntity>>

    suspend fun reIssueBuzzzzingJwt(
        refreshToken: String
    ) : Flow<Outcome<JwtEntity>>
}