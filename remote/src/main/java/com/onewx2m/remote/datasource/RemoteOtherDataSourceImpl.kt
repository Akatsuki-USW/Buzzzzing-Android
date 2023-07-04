package com.onewx2m.remote.datasource

import com.onewx2m.data.datasource.RemoteOtherDataSource
import com.onewx2m.data.model.VerifyNicknameEntity
import com.onewx2m.domain.Outcome
import com.onewx2m.domain.exception.BuzzzzingHttpException
import com.onewx2m.remote.api.OtherApi
import com.onewx2m.remote.model.response.toEntity
import com.onewx2m.remote.onFailure
import com.onewx2m.remote.onSuccess
import com.onewx2m.remote.wrapOutcomeLoadingFailure
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class RemoteOtherDataSourceImpl @Inject constructor(
    private val api: OtherApi,
) : RemoteOtherDataSource {

    override suspend fun verifyNickname(nickname: String): Flow<Outcome<VerifyNicknameEntity>> =
        flow<Outcome<VerifyNicknameEntity>> {
            api.verifyNickname(nickname).onSuccess { body ->
                emit(Outcome.Success(body.data!!.toEntity()))
            }.onFailure { exception ->
                when (exception) {
                    is BuzzzzingHttpException -> emit(handleVerifyNicknameException(exception))
                    else -> emit(Outcome.Failure(exception))
                }
            }
        }.wrapOutcomeLoadingFailure()

    private fun handleVerifyNicknameException(
        exception: BuzzzzingHttpException,
    ): Outcome<VerifyNicknameEntity> = when (exception.customStatusCode) {
        2010 -> Outcome.Success(VerifyNicknameEntity(false))
        else -> Outcome.Failure(exception)
    }
}
