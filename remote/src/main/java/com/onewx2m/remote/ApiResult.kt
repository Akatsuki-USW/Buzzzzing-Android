package com.onewx2m.remote

import com.onewx2m.domain.exception.BuzzzzingHttpException
import com.onewx2m.domain.exception.common.CommonException
import com.onewx2m.remote.model.ErrorResponse

/**
 * Success : 서버로부터 API 응답 성공
 *
 * Failure -> HttpError : 서버로부터 API 응답 실패
 *
 * Failure -> NetworkError : 네트워크 끊어짐과 같은 IOException이 발생하는 경우
 *
 * Failure -> UnknownApiError : 알 수 없는 오류
 */
sealed interface ApiResult<out T> {
    data class Success<T>(val body: T) : ApiResult<T>

    sealed interface Failure : ApiResult<Nothing> {
        data class HttpError(val code: Int, val message: String, val body: String) : Failure

        data class NetworkError(val throwable: Throwable) : Failure

        data class UnknownApiError(val throwable: Throwable) : Failure

        fun safeThrowable(): Throwable = when (this) {
            is HttpError -> IllegalStateException("$message $body")
            is NetworkError -> throwable
            is UnknownApiError -> throwable
        }
    }

    fun isSuccess(): Boolean = this is Success

    fun isFailure(): Boolean = this is Failure

    fun getOrThrow(): T {
        throwOnFailure()
        return (this as Success).body
    }

    fun getOrNull(): T? =
        when (this) {
            is Success -> body
            else -> null
        }

    fun failureOrThrow(): Failure {
        throwOnSuccess()
        return this as Failure
    }

    fun exceptionOrNull(): Throwable? =
        when (this) {
            is Failure -> safeThrowable()
            else -> null
        }

    companion object {
        fun <R> successOf(result: R): ApiResult<R> = Success(result)
    }
}

internal fun ApiResult<*>.throwOnFailure() {
    if (this is ApiResult.Failure) throw safeThrowable()
}

internal fun ApiResult<*>.throwOnSuccess() {
    if (this is ApiResult.Success) throw IllegalStateException("Cannot be called under Success conditions.")
}

inline fun <T> ApiResult<T>.onFailure(action: (error: Exception) -> Unit): ApiResult<T> {
    if (isFailure()) action(failureOrThrow().toBuzzzzingException())
    return this
}

inline fun <T> ApiResult<T>.onSuccess(action: (value: T) -> Unit): ApiResult<T> {
    if (isSuccess()) action(getOrThrow())
    return this
}

fun ApiResult.Failure.toBuzzzzingException(): Exception {
    return when (this) {
        is ApiResult.Failure.HttpError -> {
            val errorBody = KotlinSerializationUtil.json.decodeFromString<ErrorResponse>(body)
            handleHttpError(this, errorBody)
        }

        is ApiResult.Failure.NetworkError -> CommonException.NetworkException()
        is ApiResult.Failure.UnknownApiError -> CommonException.UnknownException(throwable = this.throwable)
    }
}

private fun handleHttpError(
    httpError: ApiResult.Failure.HttpError,
    errorBody: ErrorResponse,
): Exception {
    val buzzzzingHttpException = BuzzzzingHttpException(
        httpError.code,
        httpError.message,
        httpError.body,
        errorBody.statusCode,
        errorBody.message,
    )

    return when (httpError.code) {
        400 -> handle400(buzzzzingHttpException, errorBody)
        403 -> handle403(buzzzzingHttpException, errorBody)
        500, 501, 502, 503, 504, 505 -> CommonException.ServerException()
        else -> buzzzzingHttpException
    }
}

private fun handle400(buzzzzingHttpException: BuzzzzingHttpException, errorBody: ErrorResponse) =
    when (errorBody.statusCode) {
        1080 -> CommonException.NeedLoginException()
        else -> buzzzzingHttpException
    }

private fun handle403(buzzzzingHttpException: BuzzzzingHttpException, errorBody: ErrorResponse) =
    when (errorBody.statusCode) {
        2020 -> CommonException.BannedUserException()
        2030 -> CommonException.BlackListUserException()
        else -> buzzzzingHttpException
    }
