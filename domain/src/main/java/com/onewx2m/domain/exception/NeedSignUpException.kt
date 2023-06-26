package com.onewx2m.domain.exception


class NeedSignUpException(val signToken: String) : RuntimeException()