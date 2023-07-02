package com.onewx2m.login_signup.ui.signup.profileandnickname

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
    ): ProfileAndNicknameViewState {
        TODO("Not yet implemented")
    }

    fun postScrollToKeyboardHeightSideEffect() {
        postSideEffect(ProfileAndNicknameSideEffect.ScrollToKeyBoardHeight)
    }
}
