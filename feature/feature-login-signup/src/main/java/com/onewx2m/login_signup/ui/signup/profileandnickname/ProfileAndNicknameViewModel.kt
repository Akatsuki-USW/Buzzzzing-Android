package com.onewx2m.login_signup.ui.signup.profileandnickname

import com.onewx2m.core_ui.util.Regex
import com.onewx2m.design_system.components.textinputlayout.TextInputLayoutState
import com.onewx2m.feature_login_signup.R
import com.onewx2m.mvi.MviViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ProfileAndNicknameViewModel @Inject constructor() :
    MviViewModel<ProfileAndNicknameViewState, ProfileAndNicknameEvent, ProfileAndNicknameSideEffect>(
        ProfileAndNicknameViewState(),
    ) {
    override fun reduceState(
        current: ProfileAndNicknameViewState,
        event: ProfileAndNicknameEvent,
    ): ProfileAndNicknameViewState = when (event) {
        ProfileAndNicknameEvent.ChangeNicknameLayoutStateCharError -> current.copy(
            nicknameLayoutState = TextInputLayoutState.ERROR,
            nicknameLayoutHelperTextResId = com.onewx2m.core_ui.R.string.text_input_layout_nickname_error_form,
        )

        ProfileAndNicknameEvent.ChangeNicknameLayoutStateLengthError -> current.copy(
            nicknameLayoutState = TextInputLayoutState.ERROR,
            nicknameLayoutHelperTextResId = com.onewx2m.core_ui.R.string.text_input_layout_nickname_error_length,
        )

        ProfileAndNicknameEvent.ChangeNicknameLayoutStateLoading -> current.copy(
            nicknameLayoutState = TextInputLayoutState.LOADING,
            nicknameLayoutHelperTextResId = com.onewx2m.core_ui.R.string.text_input_layout_nickname_checking,
        )

        ProfileAndNicknameEvent.ChangeNicknameLayoutStateNormal -> current.copy(
            nicknameLayoutState = TextInputLayoutState.FOCUSED,
            nicknameLayoutHelperTextResId = com.onewx2m.core_ui.R.string.text_input_layout_nickname_helper_hint,
        )

        ProfileAndNicknameEvent.ChangeNicknameLayoutStateSuccess -> current.copy(
            nicknameLayoutState = TextInputLayoutState.FOCUSED,
            nicknameLayoutHelperTextResId = com.onewx2m.core_ui.R.string.text_input_layout_nickname_available_nickname,
        )

        ProfileAndNicknameEvent.ChangeNicknameLayoutStateInactive -> current.copy(
            nicknameLayoutState = TextInputLayoutState.INACTIVE,
            nicknameLayoutHelperTextResId = com.onewx2m.core_ui.R.string.text_input_layout_nickname_helper_hint,
        )
    }

    fun checkNicknameRegexAndUpdateUi(nickname: CharSequence?): Boolean {
        if (nickname.isNullOrEmpty()) {
            postEvent(ProfileAndNicknameEvent.ChangeNicknameLayoutStateInactive)
            return false
        }

        if (Regex.NICKNAME_LENGTH.toRegex().matches(nickname).not()) {
            postEvent(ProfileAndNicknameEvent.ChangeNicknameLayoutStateLengthError)
            return false
        }

        if (Regex.NICKNAME_CHAR.toRegex().matches(nickname).not()) {
            postEvent(ProfileAndNicknameEvent.ChangeNicknameLayoutStateCharError)
            return false
        }

        postEvent(ProfileAndNicknameEvent.ChangeNicknameLayoutStateNormal)
        return true
    }

    fun postScrollToKeyboardHeightSideEffect() {
        postSideEffect(ProfileAndNicknameSideEffect.ScrollToKeyBoardHeight)
    }
}
