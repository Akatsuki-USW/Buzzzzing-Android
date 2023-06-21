package com.onewx2m.domain.exception


class HttpException(val code: Int, override val message: String, val body: String) : RuntimeException()