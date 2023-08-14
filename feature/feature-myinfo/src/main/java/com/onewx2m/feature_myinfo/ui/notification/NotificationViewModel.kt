package com.onewx2m.feature_myinfo.ui.notification

import androidx.lifecycle.viewModelScope
import com.onewx2m.domain.Outcome
import com.onewx2m.domain.collectOutcome
import com.onewx2m.domain.exception.common.CommonException
import com.onewx2m.domain.model.NotificationList
import com.onewx2m.domain.usecase.GetNotificationUseCase
import com.onewx2m.domain.usecase.ReadNotificationUseCase
import com.onewx2m.mvi.MviViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NotificationViewModel @Inject constructor(
    private val readNotificationUseCase: ReadNotificationUseCase,
    private val getNotificationUseCase: GetNotificationUseCase,
) : MviViewModel<NotificationViewState, NotificationEvent, NotificationSideEffect>(
    NotificationViewState(),
) {
    fun getNotificationList() = viewModelScope.launch {
        getNotificationUseCase().collectOutcome(
            handleSuccess = ::handleGetNotificationList,
            handleFail = { handleError(it.error) },
        )
    }

    private fun handleGetNotificationList(outcome: Outcome.Success<NotificationList>) {
        postEvent(
            NotificationEvent.UpdateSpotList(
                outcome.data.toItem(),
            ),
        )
    }

    fun readNotification(notificationId: Int, spotId: Int) = viewModelScope.launch {
        readNotificationUseCase(notificationId).collectOutcome(
            handleSuccess = { handleGetNotificationList(notificationId, spotId) },
            handleFail = { handleError(it.error) },
        )
    }

    private fun handleGetNotificationList(notificationId: Int, spotId: Int) {
        val newNotificationList = state.value.notificationList.map {
            if (it.notificationId == notificationId) {
                it.copy(isRead = true)
            } else {
                it
            }
        }

        postEvent(
            NotificationEvent.UpdateSpotList(
                newNotificationList,
            ),
        )

        postSideEffect(NotificationSideEffect.GoToSpotDetailFragment(spotId))
    }

    private fun handleError(error: Throwable?) {
        when (error) {
            is CommonException.NeedLoginException -> {
                postSideEffect(NotificationSideEffect.ShowErrorToast(error.snackBarMessage))
                postSideEffect(NotificationSideEffect.GoToLoginFragment)
            }

            else -> postSideEffect(
                NotificationSideEffect.ShowErrorToast(
                    error?.message ?: CommonException.UnknownException().snackBarMessage,
                ),
            )
        }
    }

    fun popBackStack() = postSideEffect(NotificationSideEffect.PopBackStack)

    override fun reduceState(
        current: NotificationViewState,
        event: NotificationEvent,
    ): NotificationViewState = when (event) {
        is NotificationEvent.UpdateSpotList -> current.copy(
            notificationList = event.notificationList,
        )
    }
}
