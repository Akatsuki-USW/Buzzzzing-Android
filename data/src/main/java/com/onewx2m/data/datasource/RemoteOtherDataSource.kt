package com.onewx2m.data.datasource

import com.onewx2m.data.model.FileNameAndUrlEntity
import com.onewx2m.data.model.JwtEntity
import com.onewx2m.data.model.VerifyNicknameEntity
import com.onewx2m.data.model.category.EntireCategoryEntity
import com.onewx2m.domain.Outcome
import com.onewx2m.domain.enums.S3Type
import kotlinx.coroutines.flow.Flow
import java.io.File

interface RemoteOtherDataSource {

    suspend fun verifyNickname(
        nickname: String,
    ): Flow<Outcome<VerifyNicknameEntity>>

    suspend fun uploadImage(
        s3Type: S3Type,
        fileList: List<File>,
    ): Flow<Outcome<List<FileNameAndUrlEntity>>>

    suspend fun signUp(
        signToken: String,
        nickname: String,
        email: String,
        profileImageUrl: String,
    ): Flow<Outcome<JwtEntity>>

    suspend fun getEntireCategory(): Flow<Outcome<EntireCategoryEntity>>
}
