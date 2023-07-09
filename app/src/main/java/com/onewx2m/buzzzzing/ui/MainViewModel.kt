package com.onewx2m.buzzzzing.ui

import androidx.lifecycle.viewModelScope
import com.onewx2m.domain.Outcome
import com.onewx2m.domain.exception.common.CommonException
import com.onewx2m.domain.usecase.ReissueJwtUseCase
import com.onewx2m.domain.usecase.SaveEntireCategoryUseCase
import com.onewx2m.mvi.MviViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val reissueJwtUseCase: ReissueJwtUseCase,
    private val saveEntireCategoryUseCase: SaveEntireCategoryUseCase,
) : MviViewModel<MainViewState, MainEvent, MainSideEffect>(MainViewState()) {

    companion object {
        private const val HIDE_SPLASH_DELAY = 250L // 로그인에서 메인으로 넘어가기 전에 스플래시 화면이 사라지므로 약간의 딜레이를 줌.
        private const val SHOW_ERROR_BEFORE_FINISH_DELAY = 3000L
    }

    var preDrawRemoveFlag = false

    fun reissueJwtAndNavigateFragmentAndHideSplashScreen() = viewModelScope.launch {
        reissueJwtUseCase().combine(saveEntireCategoryUseCase()) { reissueOutcome, categoryOutcome ->
            when {
                reissueOutcome is Outcome.Success && categoryOutcome is Outcome.Success -> Outcome.Success(
                    Unit,
                )

                categoryOutcome is Outcome.Failure -> Outcome.Failure(categoryOutcome.error)
                else -> Outcome.Loading
            }
        }.collect { combineOutcome ->
            when (combineOutcome) {
                is Outcome.Loading -> {}
                is Outcome.Success -> postSideEffect(MainSideEffect.GoToHomeFragment)
                is Outcome.Failure -> {
                    postEvent(MainEvent.HideSplashScreen)
                    postSideEffect(
                        MainSideEffect.ShowErrorToast(
                            combineOutcome.error?.message
                                ?: CommonException.UnknownException().snackBarMessage,
                        ),
                    )
                    delay(SHOW_ERROR_BEFORE_FINISH_DELAY)
                    postSideEffect(MainSideEffect.FinishActivity)
                }
            }
        }
        delay(HIDE_SPLASH_DELAY)
        postEvent(MainEvent.HideSplashScreen)
    }

    fun isDestinationInBottomNavigationBarInitialFragment(
        destinationId: Int,
        bottomNavigationBarInitialFragmentIds: List<Int>,
    ) {
        if (destinationId in bottomNavigationBarInitialFragmentIds) {
            postEvent(MainEvent.ShowBottomNavigationBar)
        } else {
            postEvent(MainEvent.HideBottomNavigationBar)
        }
    }

    override fun reduceState(current: MainViewState, event: MainEvent): MainViewState =
        when (event) {
            is MainEvent.HideBottomNavigationBar -> current.copy(isBottomNavigationBarVisible = false)
            is MainEvent.ShowBottomNavigationBar -> current.copy(isBottomNavigationBarVisible = true)
            is MainEvent.HideSplashScreen -> current.copy(isSplashScreenVisible = false)
        }
}
