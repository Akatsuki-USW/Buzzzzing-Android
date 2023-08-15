package com.onewx2m.data.datasource

import com.onewx2m.data.model.BanEntity
import com.onewx2m.data.model.SpotListEntity
import com.onewx2m.data.model.UserInfoEntity
import com.onewx2m.domain.Outcome
import com.onewx2m.domain.enums.ReportType
import com.onewx2m.domain.model.Ban
import com.onewx2m.domain.model.SpotList
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

    suspend fun getSpotWritten(
        cursorId: Int,
    ): Flow<Outcome<SpotListEntity>>

    suspend fun getSpotCommented(
        cursorId: Int,
    ): Flow<Outcome<SpotListEntity>>

    suspend fun revoke(): Flow<Outcome<Unit>>

    suspend fun getBanReasonList(): Flow<Outcome<List<BanEntity>>>

    suspend fun logout(): Flow<Outcome<Unit>>
}
