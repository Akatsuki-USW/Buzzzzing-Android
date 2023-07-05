package com.onewx2m.domain.repository

import com.onewx2m.domain.Outcome
import com.onewx2m.domain.enums.S3Type
import com.onewx2m.domain.model.FileNameAndUrl
import com.onewx2m.domain.model.VerifyNickname
import kotlinx.coroutines.flow.Flow
import java.io.File

interface OtherRepository {

    suspend fun verifyNickname(nickname: String): Flow<Outcome<VerifyNickname>>

    suspend fun uploadImage(s3Type: S3Type, file: File): Flow<Outcome<List<FileNameAndUrl>>>
}
