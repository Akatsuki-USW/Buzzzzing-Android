package com.onewx2m.remote

import com.onewx2m.domain.Outcome
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart

fun <T> Flow<Outcome<T>>.wrapOutcomeLoadingFailure(): Flow<Outcome<T>> {
    return this.onStart {
        emit(Outcome.Loading)
    }.catch { error ->
        emit(Outcome.Failure(error))
    }
}