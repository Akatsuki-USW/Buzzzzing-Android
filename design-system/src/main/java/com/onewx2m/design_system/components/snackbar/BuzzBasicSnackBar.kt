package com.onewx2m.design_system.components.snackbar

import androidx.annotation.DrawableRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.onewx2m.design_system.R
import com.onewx2m.design_system.theme.BuzzzzingTheme
import com.onewx2m.design_system.theme.MINT
import com.onewx2m.design_system.theme.PINK
import com.onewx2m.design_system.theme.WHITE01

@Composable
fun BuzzBasicSnackBar(
    message: String = "",
    @DrawableRes icon: Int,
    backgroundColor: Color,
) {
    Column {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clip(shape = RoundedCornerShape(15.dp))
                .background(
                    color = backgroundColor,
                )
                .padding(20.dp),
        ) {
            Icon(
                modifier = Modifier.size(28.dp),
                painter = painterResource(id = icon),
                tint = WHITE01,
                contentDescription = "",
            )
            Spacer(modifier = Modifier.size(16.dp))
            Text(
                color = WHITE01,
                style = BuzzzzingTheme.typography.header4,
                text = message,
            )
        }

        Spacer(modifier = Modifier.size(20.dp))
    }
}

@Composable
fun BuzzErrorToast(message: String) {
    BuzzBasicSnackBar(
        message = message,
        icon = R.drawable.ic_toast_error,
        backgroundColor = PINK,
    )
}

@Composable
fun BuzzSuccessToast(message: String) {
    BuzzBasicSnackBar(
        message = message,
        icon = R.drawable.ic_text_input_layor_success,
        backgroundColor = MINT,
    )
}

@Preview
@Composable
fun CustomSnackbarPreview() {
    BuzzzzingTheme {
        Column {
            BuzzErrorToast(
                message = "message",
            )
            BuzzSuccessToast(message = "message")
        }
    }
}
