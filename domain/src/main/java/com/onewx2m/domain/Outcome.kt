package com.onewx2m.domain

import kotlinx.coroutines.flow.Flow

sealed class Outcome<out T> {
    data class Success<T>(val data: T) : Outcome<T>()
    data class Failure<T>(val error: Throwable?) : Outcome<T>()
}

suspend fun <T> Flow<Outcome<T>>.collectOutcome(
    beforeHandle: () -> Unit = {},
    handleSuccess: (Outcome.Success<T>) -> Unit = {},
    handleFail: (Outcome.Failure<T>) -> Unit = {},
) {
    collect { outcome ->
        beforeHandle()
        when (outcome) {
            is Outcome.Success -> handleSuccess(outcome)
            is Outcome.Failure -> handleFail(outcome)
        }
    }
}
