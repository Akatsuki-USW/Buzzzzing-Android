package com.onewx2m.domain.repository

import com.onewx2m.domain.Outcome
import com.onewx2m.domain.enums.ReportType
import com.onewx2m.domain.model.SpotList
import com.onewx2m.domain.model.UserInfo
import kotlinx.coroutines.flow.Flow
import java.io.File

interface UserRepository {
    suspend fun report(
        reportType: ReportType,
        reportTargetId: Int,
        reportedUserId: Int,
        content: String,
    ): Flow<Outcome<Unit>>

    suspend fun block(blockUserId: Int): Flow<Outcome<Unit>>

    suspend fun getMyInfo(): Flow<Outcome<UserInfo>>

    suspend fun editMyInfo(
        nickname: String,
        email: String,
        profileImageUrl: String,
        profileFile: File?,
    ): Flow<Outcome<UserInfo>>

    suspend fun getSpotWritten(
        cursorId: Int,
    ): Flow<Outcome<SpotList>>

    suspend fun getSpotCommented(
        cursorId: Int,
    ): Flow<Outcome<SpotList>>
}
