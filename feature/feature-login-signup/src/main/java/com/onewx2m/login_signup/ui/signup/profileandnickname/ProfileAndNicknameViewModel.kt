package com.onewx2m.login_signup.ui.signup.profileandnickname

import androidx.lifecycle.viewModelScope
import com.onewx2m.core_ui.R
import com.onewx2m.core_ui.util.Regex
import com.onewx2m.design_system.components.button.MainButtonState
import com.onewx2m.design_system.components.textinputlayout.TextInputLayoutState
import com.onewx2m.domain.Outcome
import com.onewx2m.domain.exception.common.CommonException
import com.onewx2m.domain.usecase.VerifyNicknameUseCase
import com.onewx2m.mvi.MviViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.async
import kotlinx.coroutines.cancelAndJoin
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileAndNicknameViewModel @Inject constructor(
    private val verifyNicknameUseCase: VerifyNicknameUseCase,
) :
    MviViewModel<ProfileAndNicknameViewState, ProfileAndNicknameEvent, ProfileAndNicknameSideEffect>(
        ProfileAndNicknameViewState(),
    ) {
    private var verifyNicknameFromServerJob: Job = Job()

    override fun reduceState(
        current: ProfileAndNicknameViewState,
        event: ProfileAndNicknameEvent,
    ): ProfileAndNicknameViewState = when (event) {
        ProfileAndNicknameEvent.ChangeNicknameLayoutStateCharError -> current.copy(
            nicknameLayoutState = TextInputLayoutState.ERROR,
            nicknameLayoutHelperTextResId = R.string.text_input_layout_nickname_error_form,
        )

        ProfileAndNicknameEvent.ChangeNicknameLayoutStateLengthError -> current.copy(
            nicknameLayoutState = TextInputLayoutState.ERROR,
            nicknameLayoutHelperTextResId = R.string.text_input_layout_nickname_error_length,
        )

        ProfileAndNicknameEvent.ChangeNicknameLayoutStateLoading -> current.copy(
            nicknameLayoutState = TextInputLayoutState.LOADING,
            nicknameLayoutHelperTextResId = R.string.text_input_layout_nickname_checking,
        )

        ProfileAndNicknameEvent.ChangeNicknameLayoutStateNormal -> current.copy(
            nicknameLayoutState = TextInputLayoutState.FOCUSED,
            nicknameLayoutHelperTextResId = R.string.text_input_layout_nickname_helper_hint,
        )

        ProfileAndNicknameEvent.ChangeNicknameLayoutStateSuccess -> current.copy(
            nicknameLayoutState = TextInputLayoutState.SUCCESS,
            nicknameLayoutHelperTextResId = R.string.text_input_layout_nickname_available_nickname,
        )

        ProfileAndNicknameEvent.ChangeNicknameLayoutStateInactive -> current.copy(
            nicknameLayoutState = TextInputLayoutState.INACTIVE,
            nicknameLayoutHelperTextResId = R.string.text_input_layout_nickname_helper_hint,
        )

        ProfileAndNicknameEvent.ChangeNicknameLayoutStateOverlap -> current.copy(
            nicknameLayoutState = TextInputLayoutState.ERROR,
            nicknameLayoutHelperTextResId = R.string.text_input_layout_nickname_overlap_nickname,
        )

        ProfileAndNicknameEvent.ChangeNicknameLayoutStateUnavailable -> current.copy(
            nicknameLayoutState = TextInputLayoutState.ERROR,
            nicknameLayoutHelperTextResId = R.string.text_input_layout_nickname_unavailable_nickname,
        )
    }

    suspend fun checkNicknameRegexAndUpdateUi(nickname: CharSequence?, isFocused: Boolean) =
        viewModelScope.async {
            if (verifyNicknameFromServerJob.isActive) {
                verifyNicknameFromServerJob.cancelAndJoin()
            }

            if (nickname.isNullOrBlank()) {
                postNicknameStateNormalOrInactiveEvent(isFocused)
                return@async false
            }

            if (Regex.NICKNAME_LENGTH.toRegex().matches(nickname).not()) {
                postEvent(ProfileAndNicknameEvent.ChangeNicknameLayoutStateLengthError)
                return@async false
            }

            if (Regex.NICKNAME_CHAR.toRegex().matches(nickname).not()) {
                postEvent(ProfileAndNicknameEvent.ChangeNicknameLayoutStateCharError)
                return@async false
            }

            postEvent(ProfileAndNicknameEvent.ChangeNicknameLayoutStateNormal)
            true
        }.await()

    fun doWhenKeyboardShow(currentScrollY: Int, additionalScroll: Int) {
        if (currentScrollY < additionalScroll) {
            postSideEffect(
                ProfileAndNicknameSideEffect.MoreScroll(
                    additionalScroll - currentScrollY,
                ),
            )
        }
    }

    fun verifyNicknameFromServer(nickname: String) {
        verifyNicknameFromServerJob = viewModelScope.launch {
            verifyNicknameUseCase(nickname).collect { outcome ->
                if (isActive.not()) return@collect
                when (outcome) {
                    Outcome.Loading -> {
                        postEvent(ProfileAndNicknameEvent.ChangeNicknameLayoutStateLoading)
                    }

                    is Outcome.Success -> {
                        if (outcome.data.isAvailable) {
                            postEvent(ProfileAndNicknameEvent.ChangeNicknameLayoutStateSuccess)
                            postSideEffect(
                                ProfileAndNicknameSideEffect.ChangeSignUpButtonState(
                                    MainButtonState.POSITIVE,
                                ),
                            )
                            postSideEffect(ProfileAndNicknameSideEffect.UpdateSignUpNickname(nickname))
                        } else {
                            postEvent(ProfileAndNicknameEvent.ChangeNicknameLayoutStateOverlap)
                        }
                    }

                    is Outcome.Failure -> {
                        handleError(outcome.error)
                        postEvent(ProfileAndNicknameEvent.ChangeNicknameLayoutStateUnavailable)
                    }
                }
            }
        }
    }

    private fun handleError(
        error: Throwable?,
        handleNotCommonException: (error: Throwable?) -> Unit = {},
    ) {
        when (error) {
            is CommonException -> handleCommonError(error)
            else -> handleNotCommonException(error)
        }
    }

    private fun handleCommonError(error: CommonException) {
        val errorToastMessage = when (error) {
            CommonException.NeedLoginException() -> CommonException.UnknownException().snackBarMessage
            else -> error.snackBarMessage
        }

        postSideEffect(ProfileAndNicknameSideEffect.ShowErrorToast(errorToastMessage))
    }

    fun postNicknameStateNormalOrInactiveEvent(isFocused: Boolean) {
        if (isFocused) {
            postEvent(ProfileAndNicknameEvent.ChangeNicknameLayoutStateNormal)
        } else {
            postEvent(ProfileAndNicknameEvent.ChangeNicknameLayoutStateInactive)
        }
    }

    fun postSignUpButtonStateDisableSideEffect() {
        postSideEffect(ProfileAndNicknameSideEffect.ChangeSignUpButtonState(MainButtonState.DISABLE))
    }
}
