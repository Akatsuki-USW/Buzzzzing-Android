package com.onewx2m.domain.exception

class DuplicateReportException(
    override val message: String = "이미 신고되었습니다."
) : RuntimeException()
