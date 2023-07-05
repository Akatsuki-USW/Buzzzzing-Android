package com.onewx2m.data.model

import com.onewx2m.domain.model.FileNameAndUrl

data class FileNameAndUrlEntity(
    val filename: String = "",
    val fileUrl: String = "",
)

fun FileNameAndUrlEntity.toDomain(): FileNameAndUrl =
    FileNameAndUrl(filename = filename, fileUrl = fileUrl)
