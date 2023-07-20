package com.onewx2m.remote.datasource

import com.google.firebase.messaging.FirebaseMessaging
import com.onewx2m.data.datasource.RemoteOtherDataSource
import com.onewx2m.data.model.FileNameAndUrlEntity
import com.onewx2m.data.model.JwtEntity
import com.onewx2m.data.model.VerifyNicknameEntity
import com.onewx2m.data.model.category.EntireCategoryEntity
import com.onewx2m.domain.Outcome
import com.onewx2m.domain.enums.S3Type
import com.onewx2m.domain.exception.BuzzzzingHttpException
import com.onewx2m.domain.exception.DuplicateNicknameException
import com.onewx2m.domain.exception.S3UploadException
import com.onewx2m.domain.exception.common.CommonException
import com.onewx2m.remote.api.OtherApi
import com.onewx2m.remote.model.request.SignUpRequest
import com.onewx2m.remote.model.response.category.toEntity
import com.onewx2m.remote.model.response.toEntity
import com.onewx2m.remote.onFailure
import com.onewx2m.remote.onSuccess
import com.onewx2m.remote.util.FormDataUtil
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import timber.log.Timber
import java.io.File
import javax.inject.Inject

class RemoteOtherDataSourceImpl @Inject constructor(
    private val api: OtherApi,
) : RemoteOtherDataSource {

    companion object {
        const val KEY_TYPE = "type"
        const val KEY_FILES = "files"
    }

    override suspend fun verifyNickname(nickname: String): Flow<Outcome<VerifyNicknameEntity>> =
        flow {
            api.verifyNickname(nickname).onSuccess { body ->
                emit(Outcome.Success(body.data!!.toEntity()))
            }.onFailure { exception ->
                when (exception) {
                    is BuzzzzingHttpException -> emit(handleVerifyNicknameException(exception))
                    else -> emit(Outcome.Failure(exception))
                }
            }
        }

    private fun handleVerifyNicknameException(
        exception: BuzzzzingHttpException,
    ): Outcome<VerifyNicknameEntity> = when (exception.customStatusCode) {
        2010 -> Outcome.Success(VerifyNicknameEntity(false))
        else -> Outcome.Failure(exception)
    }

    override suspend fun uploadImage(
        s3Type: S3Type,
        fileList: List<File>,
    ): Flow<Outcome<List<FileNameAndUrlEntity>>> = flow {
        val typeBody = FormDataUtil.getBody(KEY_TYPE, s3Type.value)
        val fileListBody = fileList.map { file -> FormDataUtil.getImageBody(KEY_FILES, file) }

        api.uploadImage(typeBody, fileListBody).onSuccess { body ->
            emit(Outcome.Success(body.data!!.files.map { it.toEntity() }))
        }.onFailure { exception ->
            when (exception) {
                is BuzzzzingHttpException -> emit(Outcome.Failure(S3UploadException()))
                else -> emit(Outcome.Failure(exception))
            }
        }
    }

    override suspend fun signUp(
        signToken: String,
        nickname: String,
        email: String,
        profileImageUrl: String,
    ): Flow<Outcome<JwtEntity>> = flow<Outcome<JwtEntity>> {
        val request = SignUpRequest(
            signToken = "Bearer $signToken",
            nickname = nickname,
            email = email,
            profileImageUrl = profileImageUrl,
            fcmToken = FirebaseMessaging.getInstance().token.await()
        )

        api.signUp(request).onSuccess { body ->
            emit(Outcome.Success(body.data!!.toEntity()))
        }.onFailure { exception ->
            if (exception is BuzzzzingHttpException) {
                emit(handleSignUpException(exception))
            } else {
                emit(Outcome.Failure(exception))
            }
        }
    }

    private fun handleSignUpException(
        exception: BuzzzzingHttpException,
    ): Outcome<JwtEntity> = when (exception.customStatusCode) {
        2010 -> Outcome.Failure(DuplicateNicknameException("회원님이 입력하신 닉네임이 그새 사용 중인 닉네임으로 변경되었어요."))
        else -> Outcome.Failure(CommonException.UnknownException())
    }

    override suspend fun getEntireCategory(): Flow<Outcome<EntireCategoryEntity>> =
        flow<Outcome<EntireCategoryEntity>> {
            api.getEntireCategory().onSuccess { body ->
                emit(Outcome.Success(body.data!!.toEntity()))
            }.onFailure {
                emit(Outcome.Failure(it))
            }
        }
}
