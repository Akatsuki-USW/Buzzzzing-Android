package com.onewx2m.login_signup.ui.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.onewx2m.core_ui.extensions.observeWithLifecycle
import com.onewx2m.design_system.components.button.SnsLoginButton
import com.onewx2m.design_system.theme.BuzzzzingTheme
import com.onewx2m.feature_login_signup.R

@Composable
fun LoginRoute(
    viewModel: LoginViewModel = hiltViewModel(),
    handleSideEffect: (sideEffect: LoginSideEffect) -> Unit = {},
) {
    val uiState: LoginViewState by viewModel.state.collectAsStateWithLifecycle()

    viewModel.sideEffects.observeWithLifecycle { sideEffect ->
        handleSideEffect(sideEffect)
    }

    LoginScreen(
        uiState = uiState,
        onClickKakaoLoginButton = viewModel::onClickKakaoLoginButton,
    )
}

@Composable
fun LoginScreen(
    uiState: LoginViewState,
    onClickKakaoLoginButton: () -> Unit = {},
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 20.dp),
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Spacer(modifier = Modifier.height(145.dp))
            Image(
                modifier = Modifier.size(189.dp),
                painter = painterResource(id = com.onewx2m.design_system.R.drawable.ic_login_logo),
                contentDescription = null,
            )
            Spacer(modifier = Modifier.height((-29).dp))
            Image(
                modifier = Modifier
                    .width(140.dp)
                    .height(38.dp),
                painter = painterResource(id = com.onewx2m.design_system.R.drawable.ic_login_app_title),
                contentDescription = null,
            )
            Spacer(modifier = Modifier.height(7.dp))
            Text(
                text = stringResource(id = R.string.fragment_login_app_introduction),
                style = BuzzzzingTheme.typography.header3,
            )
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter),
        ) {
            SnsLoginButton(
                text = stringResource(id = R.string.fragment_login_login_to_kakao),
                type = uiState.kakaoLoginButtonType,
                onClick = onClickKakaoLoginButton,
            )
            Spacer(modifier = Modifier.height(57.dp))
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFFFFF)
@Composable
fun LoginScreenPreview() {
    BuzzzzingTheme {
        LoginScreen(
            uiState = LoginViewState(),
        )
    }
}
