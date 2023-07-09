package com.onewx2m.data.extension

import com.onewx2m.domain.Outcome
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.flow

@OptIn(ExperimentalCoroutinesApi::class)
fun <T, R> Flow<Outcome<T>>.flatMapOutcomeSuccess(domainMapper: suspend (T) -> R): Flow<Outcome<R>> {
    return this.flatMapConcat { outcome ->
        flow {
            when (outcome) {
                is Outcome.Loading -> emit(Outcome.Loading)
                is Outcome.Success -> emit(Outcome.Success(domainMapper(outcome.data)))
                is Outcome.Failure -> emit(Outcome.Failure(outcome.error))
            }
        }
    }
}

@OptIn(ExperimentalCoroutinesApi::class)
fun <T, R> Flow<T>.flatMapDomain(domainMapper: suspend (T) -> R): Flow<R> {
    return this.flatMapConcat { data ->
        flow {
            emit(domainMapper(data))
        }
    }
}