package com.onewx2m.login_signup.ui.signup

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Icon
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
import com.onewx2m.design_system.components.button.BackButton
import com.onewx2m.design_system.components.button.MainButton
import com.onewx2m.design_system.components.button.MainButtonState
import com.onewx2m.design_system.theme.BLUE
import com.onewx2m.design_system.theme.BLUE_LIGHT
import com.onewx2m.design_system.theme.BuzzzzingTheme
import com.onewx2m.login_signup.ui.signup.adapter.SignUpFragmentType

@Composable
fun SignUpRoute(
    viewModel: SignUpViewModel = hiltViewModel(),
    handleSideEffect: (sideEffect: SignUpSideEffect) -> Unit = {},
) {
    val uiState: SignUpViewState by viewModel.state.collectAsStateWithLifecycle()

    viewModel.sideEffects.observeWithLifecycle { sideEffect ->
        handleSideEffect(sideEffect)
    }

    SignUpScreen(
        uiState = uiState,
    )
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun SignUpScreen(
    uiState: SignUpViewState,
) {
    val pageCount = SignUpFragmentType.values().size
    val pagerState = rememberPagerState { pageCount }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 20.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Spacer(modifier = Modifier.height(16.dp))
        Box(
            modifier = Modifier.fillMaxWidth(),
        ) {
            BackButton(modifier = Modifier.align(Alignment.CenterStart))
            PagerIndicator(
                modifier = Modifier.align(Alignment.CenterEnd),
                pageCount = pageCount,
                pagerState = pagerState,
            )
        }
        Spacer(modifier = Modifier.height(20.dp))
        HorizontalPager(
            state = pagerState,
            modifier = Modifier.weight(1f),
        ) { page ->
            when (page) {
                else -> {}
            }
        }
        MainButton(
            text = stringResource(id = com.onewx2m.design_system.R.string.word_next),
            type = MainButtonState.DISABLE,
        )
        Spacer(modifier = Modifier.height(32.dp))
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFFFFF)
@Composable
fun SignUpScreenPreview() {
    BuzzzzingTheme {
        SignUpScreen(
            uiState = SignUpViewState(),
        )
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun PagerIndicator(
    modifier: Modifier,
    pageCount: Int,
    pagerState: PagerState,
) {
    Row(
        modifier = modifier
            .wrapContentSize(),
        horizontalArrangement = Arrangement.Center,
    ) {
        repeat(pageCount) { iteration ->
            val color = if (pagerState.currentPage <= iteration) BLUE else BLUE_LIGHT
            Icon(
                modifier = Modifier.size(24.dp),
                painter = painterResource(
                    id = com.onewx2m.design_system.R.drawable.ic_airplane_blue,
                ),
                contentDescription = "",
                tint = color,
            )

            val isLastPage = iteration == pagerState.currentPage
            if (!isLastPage) {
                Spacer(modifier = Modifier.width(4.dp))
            }
        }
    }
}
