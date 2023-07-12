package com.onewx2m.remote.datasource

import com.onewx2m.data.datasource.RemoteMediaDataSource
import com.onewx2m.data.model.FileNameAndUrlEntity
import com.onewx2m.data.model.JwtEntity
import com.onewx2m.data.model.category.EntireCategoryEntity
import com.onewx2m.domain.Outcome
import com.onewx2m.domain.enums.S3Type
import com.onewx2m.domain.exception.BuzzzzingHttpException
import com.onewx2m.domain.exception.DuplicateNicknameException
import com.onewx2m.domain.exception.S3UploadException
import com.onewx2m.domain.exception.common.CommonException
import com.onewx2m.remote.api.MediaApi
import com.onewx2m.remote.model.request.SignUpRequest
import com.onewx2m.remote.model.response.category.toEntity
import com.onewx2m.remote.model.response.toEntity
import com.onewx2m.remote.onFailure
import com.onewx2m.remote.onSuccess
import com.onewx2m.remote.util.FormDataUtil
import com.onewx2m.remote.wrapOutcomeLoadingFailure
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.File
import javax.inject.Inject

class RemoteMediaDataSourceImpl @Inject constructor(
    private val api: MediaApi,
) : RemoteMediaDataSource {

    companion object {
        private const val KEY_TYPE = "type"
        private const val KEY_FILES = "files"
        private const val KEY_NEW_FILES = "newFiles"
        private const val KEY_TO_DELETE_URLS = "toDeleteUrls"
    }

    override suspend fun changeImage(
        s3Type: S3Type,
        toDeleteUrls: String,
        newFiles: List<File>,
    ): Flow<Outcome<List<FileNameAndUrlEntity>>> = flow<Outcome<List<FileNameAndUrlEntity>>> {
        val typeBody = FormDataUtil.getBody(KEY_TYPE, s3Type.value)
        val toDeleteUrlsBody = FormDataUtil.getBody(KEY_TO_DELETE_URLS, toDeleteUrls)
        val fileListBody = newFiles.map { file -> FormDataUtil.getImageBody(KEY_NEW_FILES, file) }

        api.changeImage(typeBody, toDeleteUrlsBody, fileListBody).onSuccess { body ->
            emit(Outcome.Success(body.data!!.files.map { it.toEntity() }))
        }.onFailure { exception ->
            when (exception) {
                is BuzzzzingHttpException -> emit(Outcome.Failure(S3UploadException()))
                else -> emit(Outcome.Failure(exception))
            }
        }
    }.wrapOutcomeLoadingFailure()
}
