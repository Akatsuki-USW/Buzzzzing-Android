package com.onewx2m.feature_myinfo.ui.ban

import androidx.lifecycle.viewModelScope
import com.onewx2m.design_system.components.recyclerview.ban.BanItem
import com.onewx2m.design_system.enum.ItemViewType
import com.onewx2m.domain.Outcome
import com.onewx2m.domain.collectOutcome
import com.onewx2m.domain.exception.common.CommonException
import com.onewx2m.domain.model.Ban
import com.onewx2m.domain.usecase.GetBanReasonListUseCase
import com.onewx2m.mvi.MviViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BanViewModel @Inject constructor(
    private val getBanUseCase: GetBanReasonListUseCase,
) : MviViewModel<BanViewState, BanEvent, BanSideEffect>(
    BanViewState(),
) {
    fun getBanList() = viewModelScope.launch {
        getBanUseCase().collectOutcome(
            handleSuccess = ::handleGetBanList,
            handleFail = { handleError(it.error) },
        )
    }

    private fun handleGetBanList(outcome: Outcome.Success<List<Ban>>) {
        val item =
            if (outcome.data.isEmpty()) listOf(BanItem(type = ItemViewType.EMPTY)) else outcome.data.toItem()

        postEvent(
            BanEvent.UpdateBanList(
                item,
            ),
        )
    }

    private fun handleError(error: Throwable?) {
        when (error) {
            is CommonException.NeedLoginException -> {
                postSideEffect(BanSideEffect.ShowErrorToast(error.snackBarMessage))
                postSideEffect(BanSideEffect.GoToLoginFragment)
            }

            else -> postSideEffect(
                BanSideEffect.ShowErrorToast(
                    error?.message ?: CommonException.UnknownException().snackBarMessage,
                ),
            )
        }
    }

    fun popBackStack() = postSideEffect(BanSideEffect.PopBackStack)

    override fun reduceState(
        current: BanViewState,
        event: BanEvent,
    ): BanViewState = when (event) {
        is BanEvent.UpdateBanList -> current.copy(
            banList = event.banList,
        )
    }
}
