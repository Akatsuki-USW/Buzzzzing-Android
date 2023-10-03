package com.onewx2m.design_system.components.button

import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.onewx2m.design_system.R
import com.onewx2m.design_system.theme.BuzzzzingTheme

@Composable
fun BackButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {},
) {
    IconButton(
        modifier = modifier.size(24.dp),
        onClick = onClick,
    ) {
        Icon(
            painter = painterResource(
                id = R.drawable.ic_back,
            ),
            contentDescription = "",
        )
    }
}

@Preview
@Composable
fun BackButtonPreview() {
    BuzzzzingTheme {
        BackButton()
    }
}
