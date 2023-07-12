package com.onewx2m.data.repository

import com.onewx2m.data.datasource.RemoteMediaDataSource
import com.onewx2m.data.datasource.RemoteUserDataSource
import com.onewx2m.data.extension.flatMapOutcomeSuccess
import com.onewx2m.data.model.toDomain
import com.onewx2m.domain.Outcome
import com.onewx2m.domain.enums.S3Type
import com.onewx2m.domain.model.UserInfo
import com.onewx2m.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import java.io.File
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val remoteUserDataSource: RemoteUserDataSource,
    private val remoteMediaDataSource: RemoteMediaDataSource,
) : UserRepository {
    override suspend fun getMyInfo(): Flow<Outcome<UserInfo>> {
        return remoteUserDataSource.getUserInfo().flatMapOutcomeSuccess { dataModel ->
            dataModel.toDomain()
        }
    }

    override suspend fun editMyInfo(
        nickname: String,
        email: String,
        profileImageUrl: String,
        profileFile: File?,
    ): Flow<Outcome<UserInfo>> = flow {
        var newProfileUrl = profileImageUrl

        var isUploadFileFail = false
        profileFile?.let {
            remoteMediaDataSource.changeImage(S3Type.PROFILE, profileImageUrl, listOf(it))
                .collect { outcome ->
                    when (outcome) {
                        Outcome.Loading -> {}
                        is Outcome.Success -> {
                            newProfileUrl = outcome.data[0].fileUrl
                        }

                        is Outcome.Failure -> {
                            isUploadFileFail = true
                            emit(Outcome.Failure(outcome.error))
                        }
                    }
                }
        }

        if (isUploadFileFail) return@flow

        remoteUserDataSource.editMyInfo(
            nickname = nickname,
            email = email,
            profileImageUrl = newProfileUrl,
        ).flatMapOutcomeSuccess { dataModel ->
            dataModel.toDomain()
        }.collect {
            emit(it)
        }
    }
}
