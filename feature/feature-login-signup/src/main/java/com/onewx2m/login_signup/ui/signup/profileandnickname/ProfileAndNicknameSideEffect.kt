package com.onewx2m.login_signup.ui.signup.profileandnickname

import android.net.Uri
import com.onewx2m.design_system.components.button.MainButtonState
import com.onewx2m.mvi.SideEffect

sealed interface ProfileAndNicknameSideEffect : SideEffect {
    data class MoreScroll(val scrollY: Int) : ProfileAndNicknameSideEffect
    data class ShowErrorToast(val message: String) : ProfileAndNicknameSideEffect
    data class ChangeSignUpButtonState(val buttonState: MainButtonState) : ProfileAndNicknameSideEffect
    data class UpdateSignUpNickname(val nickname: String) : ProfileAndNicknameSideEffect
    data class UpdateSignUpProfileUri(val profileUri: Uri) : ProfileAndNicknameSideEffect
    object GetPermissionAndShowImagePicker : ProfileAndNicknameSideEffect
}
