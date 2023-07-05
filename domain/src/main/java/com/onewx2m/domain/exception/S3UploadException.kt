package com.onewx2m.domain.exception

class S3UploadException(override val message: String = "이미지 서버에 문제가 있어 업로드할 수 없어요.") : RuntimeException(message)
