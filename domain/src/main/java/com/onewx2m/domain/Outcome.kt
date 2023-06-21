package com.onewx2m.domain

sealed class Outcome<out T> {
    object Loading : Outcome<Nothing>()
    data class Success<T>(val data: T) : Outcome<T>()
    data class Failure<T>(val error: Throwable?) : Outcome<T>()
}