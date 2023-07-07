package com.onewx2m.remote.datasource

import com.onewx2m.data.datasource.RemoteUserDataSource
import com.onewx2m.data.model.UserInfoEntity
import com.onewx2m.domain.Outcome
import com.onewx2m.remote.api.UserApi
import com.onewx2m.remote.model.response.toEntity
import com.onewx2m.remote.onFailure
import com.onewx2m.remote.onSuccess
import com.onewx2m.remote.wrapOutcomeLoadingFailure
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class RemoteUserDataSourceImpl @Inject constructor(
    private val api: UserApi,
) : RemoteUserDataSource {

    override suspend fun getUserInfo(): Flow<Outcome<UserInfoEntity>> =
        flow<Outcome<UserInfoEntity>> {
            api.getMyInfo().onSuccess { body ->
                emit(Outcome.Success(body.data!!.toEntity()))
            }.onFailure { exception ->
                emit(Outcome.Failure(exception))
            }
        }.wrapOutcomeLoadingFailure()
}
