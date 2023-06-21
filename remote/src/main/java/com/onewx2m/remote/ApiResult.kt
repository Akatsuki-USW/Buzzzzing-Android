package com.onewx2m.remote

import com.onewx2m.domain.exception.HttpException
import com.onewx2m.domain.exception.NetworkException
import com.onewx2m.domain.exception.UnknownException
import kotlinx.serialization.Serializable

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
    data class Success<T>(val data: ApiResponse<T>) : ApiResult<T>

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
        return (this as Success).data.data!!
    }

    fun getOrNull(): T? =
        when (this) {
            is Success -> data.data
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
        fun <R> successOf(result: R): ApiResult<R> = Success(ApiResponse(result))
    }
}

internal fun ApiResult<*>.throwOnFailure() {
    if (this is ApiResult.Failure) throw safeThrowable()
}

internal fun ApiResult<*>.throwOnSuccess() {
    if (this is ApiResult.Success) throw IllegalStateException("Cannot be called under Success conditions.")
}

inline fun <T> ApiResult<T>.onFailure(action: (error: ApiResult.Failure) -> Unit): ApiResult<T> {
    if (isFailure()) action(failureOrThrow())
    return this
}

inline fun <T> ApiResult<T>.onSuccess(action: (value: T) -> Unit): ApiResult<T> {
    if (isSuccess()) action(getOrThrow())
    return this
}

fun ApiResult.Failure.toBuzzzzingException(): RuntimeException {
    return when (this) {
        is ApiResult.Failure.HttpError -> HttpException(code, message, body)
        is ApiResult.Failure.NetworkError -> NetworkException()
        is ApiResult.Failure.UnknownApiError -> UnknownException()
    }
}

@Serializable
data class ApiResponse<D>(
    val data : D? = null,
    val message: String = "",
    val statusCode: Int = -1,
)