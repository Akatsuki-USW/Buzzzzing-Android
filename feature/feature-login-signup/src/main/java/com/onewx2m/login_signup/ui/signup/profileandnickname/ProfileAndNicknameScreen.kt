package com.onewx2m.login_signup.ui.signup.profileandnickname

import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.SnackbarData
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.onewx2m.core_ui.extensions.addFocusCleaner
import com.onewx2m.core_ui.util.Constants
import com.onewx2m.core_ui.util.LaunchedEffectWithLifecycle
import com.onewx2m.core_ui.util.PermissionManager
import com.onewx2m.design_system.components.button.MainButtonState
import com.onewx2m.design_system.components.snackbar.BuzzErrorToast
import com.onewx2m.design_system.components.textinputlayout.BuzzTextField
import com.onewx2m.design_system.modifier.buzzzzingClickable
import com.onewx2m.design_system.theme.BuzzzzingTheme
import com.onewx2m.feature_login_signup.R
import gun0912.tedimagepicker.builder.TedImagePicker
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

@OptIn(FlowPreview::class)
@Composable
fun ProfileAndNicknameRoute(
    viewModel: ProfileAndNicknameViewModel = hiltViewModel(),
    postChangeMainButtonStateEvent: (MainButtonState) -> Unit = {},
    updateSignUpNickname: (String) -> Unit = {},
    updateSignUpProfileUri: (Uri) -> Unit = {},
) {
    val context = LocalContext.current
    val uiState: ProfileAndNicknameViewState by viewModel.state.collectAsStateWithLifecycle()

    val snackState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()

    val interactionSource = remember { MutableInteractionSource() }
    val isFocused by interactionSource.collectIsFocusedAsState()

    LaunchedEffectWithLifecycle {
        snapshotFlow { uiState.nickname }
            .map { nickname ->
                viewModel.postNicknameStateNormalOrInactiveEvent(isFocused)
                viewModel.postSignUpButtonStateDisableSideEffect()
                nickname
            }
            .debounce(Constants.NICKNAME_INPUT_DEBOUNCE)
            .filter { nickname ->
                viewModel.checkNicknameRegexAndUpdateUi(
                    nickname,
                    isFocused,
                )
            }
            .onEach { nickname ->
                viewModel.verifyNicknameFromServer(nickname)
            }
            .launchIn(this)

        viewModel.sideEffects
            .onEach { sideEffect ->
                when (sideEffect) {
                    is ProfileAndNicknameSideEffect.MoreScroll -> {
                    }

                    is ProfileAndNicknameSideEffect.ShowErrorToast -> {
                        coroutineScope.launch {
                            snackState.showSnackbar(sideEffect.message)
                        }
                    }

                    is ProfileAndNicknameSideEffect.ChangeSignUpButtonState -> postChangeMainButtonStateEvent(sideEffect.buttonState)

                    is ProfileAndNicknameSideEffect.UpdateSignUpNickname -> updateSignUpNickname(sideEffect.nickname)

                    is ProfileAndNicknameSideEffect.UpdateSignUpProfileUri -> updateSignUpProfileUri(sideEffect.profileUri)

                    ProfileAndNicknameSideEffect.GetPermissionAndShowImagePicker -> {
                        PermissionManager.createGetImageAndCameraPermission {
                            TedImagePicker.with(context)
                                .start { uri ->
                                    viewModel.updateProfileUri(uri)
                                    viewModel.updateSignUpProfileUri(uri)
                                }
                        }
                    }
                }
            }
            .launchIn(this)
    }

    ProfileAndNicknameScreen(
        uiState = uiState,
        snackState = snackState,
        onNicknameChange = viewModel::updateNickname,
        onClickEditButton = viewModel::postGetPermissionAndShowImagePickerSideEffect,
        interactionSource = interactionSource,
    )
}

@Composable
fun ProfileAndNicknameScreen(
    uiState: ProfileAndNicknameViewState = ProfileAndNicknameViewState(),
    onNicknameChange: (nickname: String) -> Unit = {},
    snackState: SnackbarHostState = remember { SnackbarHostState() },
    onClickEditButton: () -> Unit = {},
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
) {
    val focusManager = LocalFocusManager.current
    val scrollState = rememberScrollState()

    Box {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .addFocusCleaner(focusManager)
                .verticalScroll(scrollState),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = stringResource(id = R.string.profile_and_nickname_title),
                style = BuzzzzingTheme.typography.header2,
            )
            Spacer(modifier = Modifier.height(70.dp))
            Box(
                modifier = Modifier.size(126.dp),
            ) {
                AsyncImage(
                    modifier = Modifier
                        .size(120.dp)
                        .align(Alignment.Center)
                        .clip(
                            shape = RoundedCornerShape(
                                20.dp,
                            ),
                        ),
                    model = uiState.profileUri
                        ?: com.onewx2m.design_system.R.drawable.ic_profile,
                    contentScale = ContentScale.Crop,
                    contentDescription = "",
                )
                Image(
                    modifier = Modifier
                        .align(Alignment.BottomEnd)
                        .padding(end = 0.dp)
                        .buzzzzingClickable(
                            onClick = onClickEditButton,
                        ),
                    painter = painterResource(
                        id = com.onewx2m.design_system.R.drawable.ic_edit,
                    ),
                    contentDescription = "",
                )
            }
            Spacer(modifier = Modifier.height(45.dp))
            BuzzTextField(
                initType = uiState.nicknameLayoutState,
                helperText = stringResource(id = uiState.nicknameLayoutHelperTextResId),
                text = uiState.nickname,
                interactionSource = interactionSource,
                onTextChange = onNicknameChange,
            )
        }

        SnackbarHost(
            modifier = Modifier.align(Alignment.BottomStart),
            hostState = snackState,
        ) { snackbarData: SnackbarData ->
            BuzzErrorToast(
                message = snackbarData.visuals.message,
            )
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFFFFF)
@Composable
fun ProfileAndNicknameScreenPreview() {
    BuzzzzingTheme {
        ProfileAndNicknameScreen(
            uiState = ProfileAndNicknameViewState(),
        )
    }
}
