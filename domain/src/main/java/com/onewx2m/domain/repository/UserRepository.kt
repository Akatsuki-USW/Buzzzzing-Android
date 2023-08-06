package com.onewx2m.domain.repository

import com.onewx2m.domain.Outcome
import com.onewx2m.domain.model.UserInfo
import kotlinx.coroutines.flow.Flow
import java.io.File

interface UserRepository {
    suspend fun block(blockUserId: Int): Flow<Outcome<Unit>>

    suspend fun getMyInfo(): Flow<Outcome<UserInfo>>

    suspend fun editMyInfo(nickname: String, email: String, profileImageUrl: String, profileFile: File?): Flow<Outcome<UserInfo>>
}
