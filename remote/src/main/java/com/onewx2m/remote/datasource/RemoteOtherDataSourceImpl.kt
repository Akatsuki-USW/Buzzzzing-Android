package com.onewx2m.remote.datasource

import com.onewx2m.data.datasource.RemoteOtherDataSource
import com.onewx2m.data.model.FileNameAndUrlEntity
import com.onewx2m.data.model.VerifyNicknameEntity
import com.onewx2m.domain.Outcome
import com.onewx2m.domain.enums.S3Type
import com.onewx2m.domain.exception.BuzzzzingHttpException
import com.onewx2m.domain.exception.S3UploadException
import com.onewx2m.remote.api.OtherApi
import com.onewx2m.remote.model.response.toEntity
import com.onewx2m.remote.onFailure
import com.onewx2m.remote.onSuccess
import com.onewx2m.remote.util.FormDataUtil
import com.onewx2m.remote.wrapOutcomeLoadingFailure
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.File
import javax.inject.Inject

class RemoteOtherDataSourceImpl @Inject constructor(
    private val api: OtherApi,
) : RemoteOtherDataSource {

    companion object {
        private const val KEY_TYPE = "type"
        private const val KEY_FILES = "files"
        private const val KEY_NEW_FILES = "newFiles"
        private const val TO_DELETE_URLS = "toDeleteUrls"
    }

    override suspend fun verifyNickname(nickname: String): Flow<Outcome<VerifyNicknameEntity>> =
        flow<Outcome<VerifyNicknameEntity>> {
            api.verifyNickname(nickname).onSuccess { body ->
                emit(Outcome.Success(body.data!!.toEntity()))
            }.onFailure { exception ->
                when (exception) {
                    is BuzzzzingHttpException -> emit(handleVerifyNicknameException(exception))
                    else -> emit(Outcome.Failure(exception))
                }
            }
        }.wrapOutcomeLoadingFailure()

    private fun handleVerifyNicknameException(
        exception: BuzzzzingHttpException,
    ): Outcome<VerifyNicknameEntity> = when (exception.customStatusCode) {
        2010 -> Outcome.Success(VerifyNicknameEntity(false))
        else -> Outcome.Failure(exception)
    }

    override suspend fun uploadImage(
        s3Type: S3Type,
        fileList: List<File>,
    ): Flow<Outcome<List<FileNameAndUrlEntity>>> = flow<Outcome<List<FileNameAndUrlEntity>>> {
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
    }.wrapOutcomeLoadingFailure()
}
