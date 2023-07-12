package com.onewx2m.data.datasource

import com.onewx2m.data.model.FileNameAndUrlEntity
import com.onewx2m.domain.Outcome
import com.onewx2m.domain.enums.S3Type
import kotlinx.coroutines.flow.Flow
import java.io.File

interface RemoteMediaDataSource {

    suspend fun changeImage(
        s3Type: S3Type,
        urlsToDelete: List<String>,
        newFiles: List<File>,
    ): Flow<Outcome<List<FileNameAndUrlEntity>>>
}
