package com.onewx2m.domain.exception


class BuzzzzingHttpException(
    val httpCode: Int,
    override val message: String,
    val httpBody: String,
    val customStatusCode: Int,
    val customMessage: String
) : RuntimeException()