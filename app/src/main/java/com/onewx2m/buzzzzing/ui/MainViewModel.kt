package com.onewx2m.buzzzzing.ui

import androidx.lifecycle.viewModelScope
import com.onewx2m.buzzzzing.service.NotificationType
import com.onewx2m.core_ui.model.NavigationParcel
import com.onewx2m.core_ui.util.BuzzzzingUser
import com.onewx2m.domain.Outcome
import com.onewx2m.domain.exception.common.CommonException
import com.onewx2m.domain.model.UserInfo
import com.onewx2m.domain.usecase.GetMyInfoUseCase
import com.onewx2m.domain.usecase.SaveEntireCategoryUseCase
import com.onewx2m.mvi.MviViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getMyInfoUseCase: GetMyInfoUseCase,
    private val saveEntireCategoryUseCase: SaveEntireCategoryUseCase,
) : MviViewModel<MainViewState, MainEvent, MainSideEffect>(MainViewState()) {

    companion object {
        private const val HIDE_SPLASH_DELAY = 300L // 로그인에서 메인으로 넘어가기 전에 스플래시 화면이 사라지므로 약간의 딜레이를 줌.
        private const val SHOW_ERROR_BEFORE_FINISH_DELAY = 3000L
    }

    private val bottomNavigationBarInitialFragmentIds =
        listOf(
            com.onewx2m.feature_home.R.id.homeFragment,
            com.onewx2m.feature_myinfo.R.id.myInfoFragment,
            com.onewx2m.recommend_place.R.id.recommendPlaceFragment,
            com.onewx2m.feature_bookmark.R.id.bookmarkFragment,
        )

    var preDrawRemoveFlag = false
    private var waitTime = 0L

    fun reissueJwtAndNavigateFragmentAndHideSplashScreen() = viewModelScope.launch {
        getMyInfoUseCase().combine(saveEntireCategoryUseCase()) { myInfoOutcome, categoryOutcome ->
            when {
                myInfoOutcome is Outcome.Success && categoryOutcome is Outcome.Success -> Outcome.Success<UserInfo>(
                    myInfoOutcome.data,
                )

                categoryOutcome is Outcome.Failure -> Outcome.Failure<UserInfo>(categoryOutcome.error)
                else -> null
            }
        }.collect { combineOutcome ->
            when (combineOutcome) {
                is Outcome.Success -> {
                    with(combineOutcome.data) {
                        BuzzzzingUser.setInfo(
                            email = email,
                            nickname = nickname,
                            profileImageUrl = profileImageUrl,
                        )
                    }
                    postSideEffect(MainSideEffect.GoToHomeFragment)
                }

                is Outcome.Failure -> {
                    postEvent(MainEvent.HideSplashScreen)
                    postSideEffect(
                        MainSideEffect.ShowErrorToast(
                            combineOutcome.error?.message
                                ?: CommonException.UnknownException().snackBarMessage,
                        ),
                    )
                }

                else -> {}
            }
        }
        delay(HIDE_SPLASH_DELAY)
        postEvent(MainEvent.HideSplashScreen)
        postSideEffect(MainSideEffect.ProcessInitIntent)
    }

    fun isDestinationInBottomNavigationBarInitialFragment(
        destinationId: Int,
    ) {
        if (destinationId in bottomNavigationBarInitialFragmentIds) {
            postEvent(MainEvent.ShowBottomNavigationBar)
            postSideEffect(MainSideEffect.ChangeBackPressedCallbackEnable(true))
        } else {
            postEvent(MainEvent.HideBottomNavigationBar)
            postSideEffect(MainSideEffect.ChangeBackPressedCallbackEnable(false))
        }
    }

    fun doWhenKeyboardVisibilityChange(imeVisible: Boolean, currentId: Int?) {
        if (imeVisible) {
            postEvent(MainEvent.HideBottomNavigationBar)
        } else {
            isDestinationInBottomNavigationBarInitialFragment(currentId ?: -1)
        }
    }

    override fun reduceState(current: MainViewState, event: MainEvent): MainViewState =
        when (event) {
            is MainEvent.HideBottomNavigationBar -> current.copy(isBottomNavigationBarVisible = false)
            is MainEvent.ShowBottomNavigationBar -> current.copy(isBottomNavigationBarVisible = true)
            is MainEvent.HideSplashScreen -> current.copy(isSplashScreenVisible = false)
        }

    fun doDelayFinish() {
        if (System.currentTimeMillis() - waitTime >= 1500) {
            waitTime = System.currentTimeMillis()
            postSideEffect(MainSideEffect.ShowBackPressedMsg)
        } else {
            postSideEffect(MainSideEffect.FinishActivity)
        }
    }

    fun processIntent(currentId: Int?, parcel: NavigationParcel) {
        if (currentId == parcel.destination) {
            postSideEffect(MainSideEffect.PopBackStack)
        }

        when (NotificationType.valueOf(parcel.type)) {
            NotificationType.CREATE_SPOT_COMMENT, NotificationType.CREATE_SPOT_COMMENT_COMMENT -> postSideEffect(
                MainSideEffect.GoToSpotDetailFragment(parcel.targetId),
            )
        }
    }
}
