package com.onewx2m.data.datasource

import com.onewx2m.data.model.UserInfoEntity
import com.onewx2m.domain.Outcome
import com.onewx2m.domain.enums.ReportType
import kotlinx.coroutines.flow.Flow

interface RemoteUserDataSource {
    suspend fun report(
        reportType: ReportType,
        reportTargetId: Int,
        reportedUserId: Int,
        content: String,
    ): Flow<Outcome<Unit>>

    suspend fun blockUser(blockUserId: Int): Flow<Outcome<Unit>>

    suspend fun getUserInfo(): Flow<Outcome<UserInfoEntity>>

    suspend fun editMyInfo(
        nickname: String,
        email: String,
        profileImageUrl: String,
    ): Flow<Outcome<UserInfoEntity>>
}
