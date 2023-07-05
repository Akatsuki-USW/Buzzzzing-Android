package com.onewx2m.data.repository

import com.onewx2m.data.datasource.RemoteOtherDataSource
import com.onewx2m.data.extension.flatMapOutcomeSuccess
import com.onewx2m.data.model.toDomain
import com.onewx2m.domain.Outcome
import com.onewx2m.domain.enums.S3Type
import com.onewx2m.domain.model.FileNameAndUrl
import com.onewx2m.domain.model.VerifyNickname
import com.onewx2m.domain.repository.OtherRepository
import kotlinx.coroutines.flow.Flow
import java.io.File
import javax.inject.Inject

class OtherRepositoryImpl @Inject constructor(
    private val remoteOtherDataSource: RemoteOtherDataSource,
) : OtherRepository {

    override suspend fun verifyNickname(nickname: String): Flow<Outcome<VerifyNickname>> {
        return remoteOtherDataSource.verifyNickname(nickname).flatMapOutcomeSuccess { dataModel ->
            dataModel.toDomain()
        }
    }

    override suspend fun uploadImage(
        s3Type: S3Type,
        fileList: List<File>,
    ): Flow<Outcome<List<FileNameAndUrl>>> {
        return remoteOtherDataSource.uploadImage(s3Type, fileList).flatMapOutcomeSuccess { dataModel ->
            dataModel.map { it.toDomain() }
        }
    }
}
