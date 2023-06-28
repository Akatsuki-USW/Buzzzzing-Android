package com.onewx2m.domain.exception.common // ktlint-disable filename

sealed interface CommonException {
    object BannedUserException : RuntimeException(), CommonException
    object BlackListUserException : RuntimeException(), CommonException
    object NeedLoginException : RuntimeException(), CommonException
    class NetworkException(throwable: Throwable) : RuntimeException(throwable), CommonException
    object ServerException : RuntimeException(), CommonException
    class UnknownException(throwable: Throwable) : RuntimeException(throwable), CommonException
}

