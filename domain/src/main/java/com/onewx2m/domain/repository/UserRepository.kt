package com.onewx2m.domain.repository

import com.onewx2m.domain.Outcome
import com.onewx2m.domain.model.UserInfo
import kotlinx.coroutines.flow.Flow

interface UserRepository {

    suspend fun getMyInfo(): Flow<Outcome<UserInfo>>
}
