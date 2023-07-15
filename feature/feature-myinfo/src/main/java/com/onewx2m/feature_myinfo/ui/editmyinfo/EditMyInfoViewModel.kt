package com.onewx2m.feature_myinfo.ui.editmyinfo

import android.net.Uri
import androidx.lifecycle.viewModelScope
import com.onewx2m.core_ui.R
import com.onewx2m.core_ui.util.BuzzzzingUser
import com.onewx2m.core_ui.util.ImageUtil
import com.onewx2m.core_ui.util.Regex
import com.onewx2m.design_system.components.button.MainButtonState
import com.onewx2m.design_system.components.textinputlayout.TextInputLayoutState
import com.onewx2m.domain.Outcome
import com.onewx2m.domain.collectOutcome
import com.onewx2m.domain.exception.common.CommonException
import com.onewx2m.domain.model.UserInfo
import com.onewx2m.domain.model.VerifyNickname
import com.onewx2m.domain.usecase.EditMyInfoUseCase
import com.onewx2m.domain.usecase.VerifyNicknameUseCase
import com.onewx2m.mvi.MviViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.async
import kotlinx.coroutines.cancelAndJoin
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EditMyInfoViewModel @Inject constructor(
    private val verifyNicknameUseCase: VerifyNicknameUseCase,
    private val editMyInfoUseCase: EditMyInfoUseCase,
    private val imageUtil: ImageUtil,
) :
    MviViewModel<EditMyInfoViewState, EditMyInfoEvent, EditMyInfoSideEffect>(
        EditMyInfoViewState(),
    ) {
    private var verifyNicknameFromServerJob: Job = Job()

    private var isApiCalling = false

    private var nickname: String = BuzzzzingUser.nickname
    private var email: String = BuzzzzingUser.email
    private var profileUri: Uri? = null

    override fun reduceState(
        current: EditMyInfoViewState,
        event: EditMyInfoEvent,
    ): EditMyInfoViewState = when (event) {
        EditMyInfoEvent.ChangeNicknameLayoutStateCharError -> current.copy(
            nicknameLayoutState = TextInputLayoutState.ERROR,
            nicknameLayoutHelperTextResId = R.string.text_input_layout_nickname_error_form,
        )

        EditMyInfoEvent.ChangeNicknameLayoutStateLengthError -> current.copy(
            nicknameLayoutState = TextInputLayoutState.ERROR,
            nicknameLayoutHelperTextResId = R.string.text_input_layout_nickname_error_length,
        )

        EditMyInfoEvent.ChangeNicknameLayoutStateLoading -> current.copy(
            nicknameLayoutState = TextInputLayoutState.LOADING,
            nicknameLayoutHelperTextResId = R.string.text_input_layout_nickname_checking,
        )

        EditMyInfoEvent.ChangeNicknameLayoutStateNormal -> current.copy(
            nicknameLayoutState = TextInputLayoutState.FOCUSED,
            nicknameLayoutHelperTextResId = R.string.text_input_layout_nickname_helper_hint,
        )

        EditMyInfoEvent.ChangeNicknameLayoutStateSuccess -> current.copy(
            nicknameLayoutState = TextInputLayoutState.SUCCESS,
            nicknameLayoutHelperTextResId = R.string.text_input_layout_nickname_available_nickname,
        )

        EditMyInfoEvent.ChangeNicknameLayoutStateInactive -> current.copy(
            nicknameLayoutState = TextInputLayoutState.INACTIVE,
            nicknameLayoutHelperTextResId = R.string.text_input_layout_nickname_helper_hint,
        )

        EditMyInfoEvent.ChangeNicknameLayoutStateOverlap -> current.copy(
            nicknameLayoutState = TextInputLayoutState.ERROR,
            nicknameLayoutHelperTextResId = R.string.text_input_layout_nickname_overlap_nickname,
        )

        EditMyInfoEvent.ChangeNicknameLayoutStateUnavailable -> current.copy(
            nicknameLayoutState = TextInputLayoutState.ERROR,
            nicknameLayoutHelperTextResId = R.string.text_input_layout_nickname_unavailable_nickname,
        )

        is EditMyInfoEvent.UpdateProfileUri -> current.copy(
            profileUri = event.uri,
        )

        EditMyInfoEvent.ChangeEmailLayoutStateInactive -> current.copy(
            emailLayoutState = TextInputLayoutState.INACTIVE,
            emailLayoutHelperTextResId = com.onewx2m.design_system.R.string.word_space,
        )

        EditMyInfoEvent.ChangeEmailLayoutStateNormal -> current.copy(
            emailLayoutState = TextInputLayoutState.FOCUSED,
            emailLayoutHelperTextResId = com.onewx2m.design_system.R.string.word_space,
        )

        EditMyInfoEvent.ChangeEmailLayoutStateSuccess -> current.copy(
            emailLayoutState = TextInputLayoutState.SUCCESS,
            emailLayoutHelperTextResId = R.string.text_input_layout_email_available_email,
        )

        EditMyInfoEvent.ChangeEmailLayoutStateUnavailable -> current.copy(
            emailLayoutState = TextInputLayoutState.ERROR,
            emailLayoutHelperTextResId = R.string.text_input_layout_email_unavailable_email,
        )

        is EditMyInfoEvent.ChangeMainButtonState -> current.copy(
            mainButtonState = event.state,
        )

        EditMyInfoEvent.ShowScrollViewAndHideLottie -> current.copy(
            isScrollViewVisible = true,
            isLottieVisible = false,
        )

        EditMyInfoEvent.HideScrollViewAndShowLottie -> current.copy(
            isScrollViewVisible = false,
            isLottieVisible = true,
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
                postEvent(EditMyInfoEvent.ChangeNicknameLayoutStateLengthError)
                return@async false
            }

            if (Regex.NICKNAME_CHAR.toRegex().matches(nickname).not()) {
                postEvent(EditMyInfoEvent.ChangeNicknameLayoutStateCharError)
                return@async false
            }

            postEvent(EditMyInfoEvent.ChangeNicknameLayoutStateNormal)
            true
        }.await()

    fun verifyNicknameFromServer(nickname: String) {
        if (nickname == BuzzzzingUser.nickname) {
            this.nickname = nickname
            postEvent(EditMyInfoEvent.ChangeNicknameLayoutStateSuccess)
            return
        }

        verifyNicknameFromServerJob = viewModelScope.launch {
            verifyNicknameUseCase(nickname)
                .onStart {
                    postEvent(EditMyInfoEvent.ChangeNicknameLayoutStateLoading)
                    beforeHandleVerifyNickname()
                }
                .collectOutcome(
                    handleSuccess = { handleVerifyNicknameSuccess(it, nickname) },
                    handleFail = { handleVerifyNicknameFail(it) },
                )
        }
    }

    private fun beforeHandleVerifyNickname() {
        if (verifyNicknameFromServerJob.isActive.not()) return
    }

    private fun handleVerifyNicknameSuccess(
        outcome: Outcome.Success<VerifyNickname>,
        nickname: String,
    ) {
        if (outcome.data.isAvailable) {
            handleNicknameIsAvailable(nickname)
        } else {
            postEvent(EditMyInfoEvent.ChangeNicknameLayoutStateOverlap)
        }
    }

    private fun handleNicknameIsAvailable(nickname: String) {
        this.nickname = nickname
        if (state.value.emailLayoutState == TextInputLayoutState.SUCCESS) {
            postChangeMainButtonStateEvent(
                MainButtonState.POSITIVE,
            )
        }
        postEvent(EditMyInfoEvent.ChangeNicknameLayoutStateSuccess)
    }

    private fun handleVerifyNicknameFail(outcome: Outcome.Failure<VerifyNickname>) {
        handleError(outcome.error)
        postEvent(EditMyInfoEvent.ChangeNicknameLayoutStateUnavailable)
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

    fun postNicknameStateNormalOrInactiveEvent(isFocused: Boolean) {
        if (isFocused) {
            postEvent(EditMyInfoEvent.ChangeNicknameLayoutStateNormal)
        } else {
            postEvent(EditMyInfoEvent.ChangeNicknameLayoutStateInactive)
        }
    }

    fun postGetPermissionAndShowImagePickerSideEffect() {
        postSideEffect(EditMyInfoSideEffect.GetPermissionAndShowImagePicker)
    }

    fun updateProfileUri(uri: Uri) {
        profileUri = uri
        postEvent(EditMyInfoEvent.UpdateProfileUri(uri))
    }

    private fun handleCommonError(error: CommonException) {
        val errorToastMessage = when (error) {
            CommonException.NeedLoginException() -> CommonException.UnknownException().snackBarMessage
            else -> error.snackBarMessage
        }

        postSideEffect(EditMyInfoSideEffect.ShowErrorToast(errorToastMessage))
    }

    fun checkEmailRegexAndUpdateUi(email: CharSequence?, isFocused: Boolean) {
        if (email.isNullOrEmpty()) {
            postEmailStateNormalOrInactiveEvent(isFocused)
            return
        }

        if (Regex.EMAIL.toRegex().matches(email).not()) {
            postEvent(EditMyInfoEvent.ChangeEmailLayoutStateUnavailable)
            return
        }

        this.email = email.toString()
        if (state.value.nicknameLayoutState == TextInputLayoutState.SUCCESS) {
            postChangeMainButtonStateEvent(
                MainButtonState.POSITIVE,
            )
        }
        postEvent(EditMyInfoEvent.ChangeEmailLayoutStateSuccess)
        return
    }

    fun postEmailStateNormalOrInactiveEvent(isFocused: Boolean) {
        if (isFocused) {
            postEvent(EditMyInfoEvent.ChangeEmailLayoutStateNormal)
        } else {
            postEvent(EditMyInfoEvent.ChangeEmailLayoutStateInactive)
        }
    }

    fun postChangeMainButtonStateEvent(mainButtonState: MainButtonState) {
        if (isApiCalling && mainButtonState != MainButtonState.LOADING) return
        postEvent(EditMyInfoEvent.ChangeMainButtonState(mainButtonState))
    }

    fun goToPrev() {
        postSideEffect(EditMyInfoSideEffect.GoToPrev)
    }

    fun doWhenKeyboardShow(currentScrollY: Int, additionalScroll: Int) {
        if (currentScrollY < additionalScroll) {
            postSideEffect(
                EditMyInfoSideEffect.MoreScroll(
                    additionalScroll - currentScrollY,
                ),
            )
        }
    }

    fun editMyInfo() = viewModelScope.launch {
        postEvent(EditMyInfoEvent.ChangeMainButtonState(MainButtonState.LOADING))
        postEvent(EditMyInfoEvent.HideScrollViewAndShowLottie)
        postSideEffect(EditMyInfoSideEffect.PlayLottie)
        postSideEffect(EditMyInfoSideEffect.HideKeyboard)

        val file = profileUri?.let {
            val nullableFile = imageUtil.uriToOptimizeImageFile(it)
            if (nullableFile == null) {
                handleEditMyInfoFail(Outcome.Failure(CommonException.UnknownException()))
                return@let null
            }
            nullableFile
        }

        editMyInfoUseCase(
            nickname = nickname,
            email = email,
            profileImageUrl = BuzzzzingUser.profileImageUrl,
            profileFile = file,
        ).collectOutcome(
            handleSuccess = ::handleEditMyInfoSuccess,
            handleFail = ::handleEditMyInfoFail,
        )
    }

    private fun handleEditMyInfoSuccess(outcome: Outcome.Success<UserInfo>) {
        postSideEffect(EditMyInfoSideEffect.GoToPrev)
        outcome.data.also {
            BuzzzzingUser.setInfo(
                email = it.email,
                nickname = it.nickname,
                profileImageUrl = it.profileImageUrl,
            )
        }
    }

    private fun handleEditMyInfoFail(outcome: Outcome.Failure<UserInfo>) {
        postEvent(EditMyInfoEvent.ChangeMainButtonState(MainButtonState.POSITIVE))
        postEvent(EditMyInfoEvent.ShowScrollViewAndHideLottie)
        postSideEffect(EditMyInfoSideEffect.StopLottie)
        postSideEffect(
            EditMyInfoSideEffect.ShowErrorToast(
                outcome.error?.message
                    ?: CommonException.UnknownException().snackBarMessage,
            ),
        )
    }
}
