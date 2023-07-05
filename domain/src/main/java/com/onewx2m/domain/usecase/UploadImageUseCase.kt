package com.onewx2m.domain.usecase

import com.onewx2m.domain.enums.S3Type
import com.onewx2m.domain.repository.OtherRepository
import java.io.File
import javax.inject.Inject

class UploadImageUseCase @Inject constructor(
    private val otherRepository: OtherRepository,
) {
    suspend operator fun invoke(s3Type: S3Type, file: File) =
        otherRepository.uploadImage(s3Type, file)
}
