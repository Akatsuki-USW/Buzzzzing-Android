package com.onewx2m.login_signup.ui.signup.termsandconditions

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.onewx2m.core_ui.util.LaunchedEffectWithLifecycle
import com.onewx2m.design_system.components.button.MainButtonState
import com.onewx2m.design_system.components.checkbox.CheckboxAgreementText
import com.onewx2m.design_system.modifier.buzzzzingClickable
import com.onewx2m.design_system.theme.BLACK01
import com.onewx2m.design_system.theme.BLUE
import com.onewx2m.design_system.theme.BLUE_LIGHT
import com.onewx2m.design_system.theme.BuzzzzingTheme
import com.onewx2m.feature_login_signup.R
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@Composable
fun TermsAndConditionsRoute(
    viewModel: TermsAndConditionsViewModel = hiltViewModel(),
    postChangeMainButtonStateEvent: (MainButtonState) -> Unit = {},
) {
    val uiState: TermsAndConditionsViewState by viewModel.state.collectAsStateWithLifecycle()
    val context = LocalContext.current

    LaunchedEffectWithLifecycle {
        viewModel.sideEffects
            .onEach { sideEffect ->
                when (sideEffect) {
                    TermsAndConditionsSideEffect.GoToPersonalInformationHandlingPolicyWebSite -> {
                        val intent = Intent(
                            Intent.ACTION_VIEW,
                            Uri.parse(context.getString(com.onewx2m.core_ui.R.string.url_personal_information_handling_policy)),
                        )
                        context.startActivity(intent)
                    }

                    TermsAndConditionsSideEffect.GoToTermsAndConditionsWebSite -> {
                        val intent = Intent(
                            Intent.ACTION_VIEW,
                            Uri.parse(context.getString(com.onewx2m.core_ui.R.string.url_terms_and_conditions)),
                        )
                        context.startActivity(intent)
                    }

                    is TermsAndConditionsSideEffect.ChangeSignUpButtonState -> {
                        postChangeMainButtonStateEvent(sideEffect.signUpButtonState)
                    }

                    TermsAndConditionsSideEffect.DoReRender -> {}
                }
            }
            .launchIn(this)
    }

    TermsAndConditionsScreen(
        uiState = uiState,
        changeAgreeAllCheckboxState = viewModel::changeAgreeAllCheckboxState,
        changePersonalHandlingPolicyState = viewModel::changePersonalHandlingPolicyState,
        changeTermsAndConditionsCheckboxState = viewModel::changeTermsAndConditionsCheckboxState,
        changeOver14CheckboxState = viewModel::changeOver14CheckboxState,
        postChangeMainButtonStateEvent = postChangeMainButtonStateEvent,
        onClickPersonalHandling = viewModel::goToPersonalInformationHandlingPolicyWebSite,
        onClickTermsAndConditions = viewModel::goToTermsAndConditionsWebSite,
    )
}

@Composable
fun TermsAndConditionsScreen(
    uiState: TermsAndConditionsViewState,
    changeAgreeAllCheckboxState: (isChecked: Boolean) -> Unit = {},
    changePersonalHandlingPolicyState: (isChecked: Boolean) -> Unit = {},
    changeTermsAndConditionsCheckboxState: (isChecked: Boolean) -> Unit = {},
    changeOver14CheckboxState: (isChecked: Boolean) -> Unit = {},
    postChangeMainButtonStateEvent: (MainButtonState) -> Unit = {},
    onClickPersonalHandling: () -> Unit = {},
    onClickTermsAndConditions: () -> Unit = {},
) {
    LaunchedEffect(key1 = uiState.isChildrenItemsAllChecked) {
        val mainButtonState =
            if (uiState.isChildrenItemsAllChecked) MainButtonState.POSITIVE else MainButtonState.DISABLE

        postChangeMainButtonStateEvent(mainButtonState)
    }

    Column(
        modifier = Modifier.fillMaxSize(),
    ) {
        Text(
            text = stringResource(id = R.string.terms_and_conditions_title),
            style = BuzzzzingTheme.typography.header2,
        )
        Spacer(modifier = Modifier.size(57.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Text(
                text = stringResource(id = com.onewx2m.design_system.R.string.word_agree_all),
                style = BuzzzzingTheme.typography.header3,
            )

            Icon(
                modifier = Modifier
                    .size(28.dp)
                    .buzzzzingClickable(rippleEnabled = false) {
                        changeAgreeAllCheckboxState(!uiState.isChildrenItemsAllChecked)
                    },
                painter = painterResource(
                    id = com.onewx2m.design_system.R.drawable.ic_check_active,
                ),
                contentDescription = "",
                tint = if (uiState.isChildrenItemsAllChecked) BLUE else BLUE_LIGHT,
            )
        }
        Spacer(modifier = Modifier.size(16.dp))
        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .height(1.dp)
                .background(color = BLACK01),
        )
        Spacer(modifier = Modifier.size(24.dp))
        CheckboxAgreementText(
            text = stringResource(id = com.onewx2m.design_system.R.string.word_personal_information_handling_policy),
            checked = uiState.isPersonalHandlingPolicyChecked,
            onCheckedChange = changePersonalHandlingPolicyState,
            onTextClick = onClickPersonalHandling,
        )
        Spacer(modifier = Modifier.size(22.dp))
        CheckboxAgreementText(
            text = stringResource(id = com.onewx2m.design_system.R.string.word_terms_and_conditions),
            checked = uiState.isTermsAndConditionsChecked,
            onCheckedChange = changeTermsAndConditionsCheckboxState,
            onTextClick = onClickTermsAndConditions,
        )
        Spacer(modifier = Modifier.size(22.dp))
        CheckboxAgreementText(
            hideArrow = true,
            text = stringResource(id = R.string.terms_and_conditions_over_14),
            checked = uiState.isOver14Checked,
            onCheckedChange = changeOver14CheckboxState,
        )
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFFFFF)
@Composable
fun TermsAndConditionsScreenPreview() {
    BuzzzzingTheme {
        TermsAndConditionsScreen(
            uiState = TermsAndConditionsViewState(),
        )
    }
}
