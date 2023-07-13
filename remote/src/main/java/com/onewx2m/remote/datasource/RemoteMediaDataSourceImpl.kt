package com.onewx2m.remote.datasource

import com.onewx2m.data.datasource.RemoteMediaDataSource
import com.onewx2m.data.model.FileNameAndUrlEntity
import com.onewx2m.domain.Outcome
import com.onewx2m.domain.enums.S3Type
import com.onewx2m.domain.exception.BuzzzzingHttpException
import com.onewx2m.domain.exception.S3UploadException
import com.onewx2m.remote.api.MediaApi
import com.onewx2m.remote.datasource.RemoteOtherDataSourceImpl.Companion.KEY_TYPE
import com.onewx2m.remote.model.response.toEntity
import com.onewx2m.remote.onFailure
import com.onewx2m.remote.onSuccess
import com.onewx2m.remote.util.FormDataUtil
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.File
import javax.inject.Inject

class RemoteMediaDataSourceImpl @Inject constructor(
    private val api: MediaApi,
) : RemoteMediaDataSource {

    companion object {
        const val KEY_NEW_FILES = "newFiles"
        const val KEY_URLS_TO_DELETE = "urlsToDelete"
    }

    override suspend fun changeImage(
        s3Type: S3Type,
        urlsToDelete: List<String>,
        newFiles: List<File>,
    ): Flow<Outcome<List<FileNameAndUrlEntity>>> = flow {
        val typeBody = FormDataUtil.getBody(KEY_TYPE, s3Type.value)
        val urlsToDeleteBody = urlsToDelete.map { toDeleteUrl -> FormDataUtil.getBody(KEY_URLS_TO_DELETE, toDeleteUrl) }
        val fileListBody = newFiles.map { file -> FormDataUtil.getImageBody(KEY_NEW_FILES, file) }

        api.changeImage(type = typeBody, urlsToDelete = urlsToDeleteBody, files = fileListBody).onSuccess { body ->
            emit(Outcome.Success(body.data!!.files.map { it.toEntity() }))
        }.onFailure { exception ->
            when (exception) {
                is BuzzzzingHttpException -> emit(Outcome.Failure(S3UploadException()))
                else -> emit(Outcome.Failure(exception))
            }
        }
    }
}
