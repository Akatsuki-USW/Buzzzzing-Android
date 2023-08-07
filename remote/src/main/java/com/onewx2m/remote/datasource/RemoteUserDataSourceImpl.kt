package com.onewx2m.remote.datasource

import com.onewx2m.data.datasource.RemoteUserDataSource
import com.onewx2m.data.model.SpotListEntity
import com.onewx2m.data.model.UserInfoEntity
import com.onewx2m.domain.Outcome
import com.onewx2m.domain.enums.ReportType
import com.onewx2m.domain.exception.BuzzzzingHttpException
import com.onewx2m.domain.exception.DuplicateReportException
import com.onewx2m.domain.exception.common.CommonException
import com.onewx2m.remote.api.UserApi
import com.onewx2m.remote.model.request.BlockUserRequest
import com.onewx2m.remote.model.request.ReportRequest
import com.onewx2m.remote.model.request.UserInfoRequest
import com.onewx2m.remote.model.response.toEntity
import com.onewx2m.remote.onFailure
import com.onewx2m.remote.onSuccess
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class RemoteUserDataSourceImpl @Inject constructor(
    private val api: UserApi,
) : RemoteUserDataSource {

    override suspend fun getUserInfo(): Flow<Outcome<UserInfoEntity>> =
        flow {
            api.getMyInfo().onSuccess { body ->
                emit(Outcome.Success(body.data!!.toEntity()))
            }.onFailure { exception ->
                emit(Outcome.Failure(exception))
            }
        }

    override suspend fun editMyInfo(
        nickname: String,
        email: String,
        profileImageUrl: String,
    ): Flow<Outcome<UserInfoEntity>> = flow {
        api.editMyInfo(
            UserInfoRequest(
                nickname = nickname,
                email = email,
                profileImageUrl = profileImageUrl,
            ),
        ).onSuccess { body ->
            emit(Outcome.Success(body.data!!.toEntity()))
        }.onFailure { exception ->
            emit(Outcome.Failure(exception))
        }
    }

    override suspend fun blockUser(blockUserId: Int): Flow<Outcome<Unit>> = flow {
        api.blockUser(BlockUserRequest(blockUserId)).onSuccess {
            emit(Outcome.Success(it.data!!))
        }.onFailure { exception ->
            emit(Outcome.Failure(exception))
        }
    }

    override suspend fun report(
        reportType: ReportType,
        reportTargetId: Int,
        reportedUserId: Int,
        content: String,
    ): Flow<Outcome<Unit>> = flow {
        api.report(
            ReportRequest(
                reportTarget = reportType.name,
                reportTargetId = reportTargetId,
                reportedUserId = reportedUserId,
                content = content,
            ),
        ).onSuccess {
            emit(Outcome.Success(it.data!!))
        }.onFailure { exception ->
            if (exception is BuzzzzingHttpException) {
                emit(handleReportException(exception))
            } else {
                emit(Outcome.Failure(exception))
            }
        }
    }

    private fun <T> handleReportException(
        exception: BuzzzzingHttpException,
    ): Outcome<T> = when (exception.httpCode) {
        400 -> Outcome.Failure(DuplicateReportException())
        else -> Outcome.Failure(CommonException.UnknownException())
    }

    override suspend fun getSpotWritten(cursorId: Int): Flow<Outcome<SpotListEntity>> = flow {
        api.getSpotWritten(cursorId).onSuccess {
            emit(Outcome.Success(it.data!!.toEntity()))
        }.onFailure {
            emit(Outcome.Failure(it))
        }
    }

    override suspend fun getSpotCommented(cursorId: Int): Flow<Outcome<SpotListEntity>> = flow {
        api.getSpotCommented(cursorId).onSuccess {
            emit(Outcome.Success(it.data!!.toEntity()))
        }.onFailure {
            emit(Outcome.Failure(it))
        }
    }
}
