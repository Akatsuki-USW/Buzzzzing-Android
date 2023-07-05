package com.onewx2m.remote.model.response

import com.onewx2m.data.model.FileNameAndUrlEntity
import kotlinx.serialization.Serializable

@Serializable
data class UploadFileListResponse(
    val files: List<UploadFileResponse> = emptyList(),
)

@Serializable
data class UploadFileResponse(
    val filename: String = "",
    val fileUrl: String = "",
)

fun UploadFileResponse.toEntity(): FileNameAndUrlEntity = FileNameAndUrlEntity(
    filename = filename,
    fileUrl = fileUrl
)
