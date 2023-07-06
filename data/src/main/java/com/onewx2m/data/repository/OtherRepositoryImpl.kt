package com.onewx2m.data.repository

import com.onewx2m.data.datasource.LocalAuthDataSource
import com.onewx2m.data.datasource.RemoteOtherDataSource
import com.onewx2m.data.extension.flatMapOutcomeSuccess
import com.onewx2m.data.model.toDomain
import com.onewx2m.domain.Outcome
import com.onewx2m.domain.enums.S3Type
import com.onewx2m.domain.model.VerifyNickname
import com.onewx2m.domain.repository.OtherRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import java.io.File
import javax.inject.Inject

class OtherRepositoryImpl @Inject constructor(
    private val remoteOtherDataSource: RemoteOtherDataSource,
    private val localAuthDataSource: LocalAuthDataSource,
) : OtherRepository {

    override suspend fun verifyNickname(nickname: String): Flow<Outcome<VerifyNickname>> {
        return remoteOtherDataSource.verifyNickname(nickname).flatMapOutcomeSuccess { dataModel ->
            dataModel.toDomain()
        }
    }

    override suspend fun signUp(
        file: File?,
        signToken: String,
        nickname: String,
        email: String,
    ): Flow<Outcome<Unit>> = flow {
        var profileImageUrl: String = ""

        var isUploadFileFail = false
        file?.let {
            remoteOtherDataSource.uploadImage(S3Type.PROFILE, listOf(it))
                .collect { outcome ->
                    when (outcome) {
                        Outcome.Loading -> {}
                        is Outcome.Success -> {
                            profileImageUrl = outcome.data[0].fileUrl
                        }

                        is Outcome.Failure -> {
                            isUploadFileFail = true
                            emit(Outcome.Failure(outcome.error))
                        }
                    }
                }
        }

        if (isUploadFileFail) return@flow

        remoteOtherDataSource.signUp(
            signToken = signToken,
            nickname = nickname,
            email = email,
            profileImageUrl = profileImageUrl,
        ).collect { outcome ->
            when (outcome) {
                Outcome.Loading -> {}
                is Outcome.Success -> {
                    localAuthDataSource.saveToken(outcome.data.toDomain())
                    emit(Outcome.Success(Unit))
                }

                is Outcome.Failure -> emit(Outcome.Failure(outcome.error))
            }
        }
    }
}
