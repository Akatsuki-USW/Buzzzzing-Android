package com.onewx2m.data.datasource

import com.onewx2m.data.model.UserInfoEntity
import com.onewx2m.domain.Outcome
import kotlinx.coroutines.flow.Flow

interface RemoteUserDataSource {

    suspend fun getUserInfo(): Flow<Outcome<UserInfoEntity>>
}
