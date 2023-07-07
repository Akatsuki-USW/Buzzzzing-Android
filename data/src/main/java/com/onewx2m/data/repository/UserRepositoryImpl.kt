package com.onewx2m.data.repository

import com.onewx2m.data.datasource.RemoteUserDataSource
import com.onewx2m.data.extension.flatMapOutcomeSuccess
import com.onewx2m.data.model.toDomain
import com.onewx2m.domain.Outcome
import com.onewx2m.domain.model.UserInfo
import com.onewx2m.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val remoteUserDataSource: RemoteUserDataSource,
) : UserRepository {
    override suspend fun getMyInfo(): Flow<Outcome<UserInfo>> {
        return remoteUserDataSource.getUserInfo().flatMapOutcomeSuccess { dataModel ->
            dataModel.toDomain()
        }
    }
}
