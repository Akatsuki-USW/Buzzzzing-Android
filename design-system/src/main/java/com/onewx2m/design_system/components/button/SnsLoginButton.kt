package com.onewx2m.design_system.components.button

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import com.onewx2m.core_ui.extensions.onThrottleClick
import com.onewx2m.design_system.R
import com.onewx2m.design_system.databinding.ButtonSnsLoginBinding
import com.onewx2m.design_system.modifier.buzzzzingClickable
import com.onewx2m.design_system.theme.BLACK01
import com.onewx2m.design_system.theme.BLACK02
import com.onewx2m.design_system.theme.BuzzzzingTheme
import com.onewx2m.design_system.theme.GRAY05
import com.onewx2m.design_system.theme.KAKAO
import com.onewx2m.design_system.theme.KAKAO_LOADING
import com.onewx2m.design_system.theme.WHITE02
import com.onewx2m.design_system.util.runIf

enum class SnsLoginButtonType(
    val textColor: Color,
    val backgroundColor: Color,
) {
    ENABLE(
        textColor = BLACK01,
        backgroundColor = KAKAO,
    ),
    LOADING(
        textColor = GRAY05,
        backgroundColor = KAKAO_LOADING,
    ),
}

@Composable
fun SnsLoginButton(
    modifier: Modifier = Modifier,
    type: SnsLoginButtonType = SnsLoginButtonType.ENABLE,
    rippleColor: Color = Color.Unspecified,
    @DrawableRes icon: Int = R.drawable.ic_kakao,
    text: String,
    onClick: () -> Unit = {},
) {
    val isLoading = type == SnsLoginButtonType.LOADING

    Box(
        modifier = modifier
            .background(
                color = type.backgroundColor,
                shape = RoundedCornerShape(5.dp),
            )
            .fillMaxWidth()
            .height(50.dp)
            .runIf(!isLoading) {
                buzzzzingClickable(
                    rippleEnabled = true,
                    rippleColor = rippleColor,
                    onClick = onClick,
                )
            },
    ) {
        Row(
            modifier = Modifier.fillMaxSize().padding(horizontal = 24.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            if (isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier.size(20.dp),
                    strokeWidth = 3.dp,
                    color = type.textColor,
                    trackColor = Color.Transparent,
                )
            } else {
                Image(
                    painter = painterResource(
                        id = icon,
                    ),
                    contentDescription = "",
                )
            }
            Text(
                modifier = Modifier.fillMaxWidth().padding(horizontal = 20.dp),
                textAlign = TextAlign.Center,
                text = text,
                color = type.textColor,
                style = BuzzzzingTheme.typography.header3,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )
        }
    }
}

@Preview
@Composable
fun SnsLoginButtonEnablePreview() {
    BuzzzzingTheme {
        SnsLoginButton(
            text = "카카오 로그인",
        )
    }
}

@Preview
@Composable
fun SnsLoginButtonLoadingPreview() {
    BuzzzzingTheme {
        SnsLoginButton(
            type = SnsLoginButtonType.LOADING,
            text = "카카오 로그인",
        )
    }
}

class SnsLoginButton @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
) :
    FrameLayout(context, attrs, defStyleAttr) {

    fun onThrottleClick(onClick: () -> Unit) =
        binding.constraintLayoutButton.onThrottleClick {
            onClick()
        }

    private val binding: ButtonSnsLoginBinding

    var state: SnsLoginButtonType = SnsLoginButtonType.ENABLE
        set(value) {
            field = value
            when (field) {
                SnsLoginButtonType.ENABLE -> {
                    changeStateToEnable()
                }

                SnsLoginButtonType.LOADING -> {
                    changeStateToLoading()
                }
            }
        }

    private fun changeStateToEnable() {
        binding.apply {
            constraintLayoutButton.isClickable = true
            constraintLayoutButton.isFocusable = true
            progressBarLoadingButton.visibility = View.INVISIBLE
            viewLoading.visibility = View.INVISIBLE
            imageViewSnsLogo.visibility = View.VISIBLE
        }
    }

    private fun changeStateToLoading() {
        binding.apply {
            constraintLayoutButton.isClickable = false
            constraintLayoutButton.isFocusable = false
            progressBarLoadingButton.visibility = View.VISIBLE
            viewLoading.visibility = View.VISIBLE
            imageViewSnsLogo.visibility = View.INVISIBLE
        }
    }

    init {
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        binding = ButtonSnsLoginBinding.inflate(inflater, this, true)

        val typedArray =
            context.obtainStyledAttributes(attrs, R.styleable.SnsLoginButton, defStyleAttr, 0)

        val background = typedArray.getResourceId(
            R.styleable.SnsLoginButton_loginButtonBackground,
            R.drawable.ripple_gray01_bg_solid_kakao01_rounded_5,
        )
        val progressBarTint = typedArray.getResourceId(
            R.styleable.SnsLoginButton_loginButtonProgressBarTint,
            R.color.black01,
        )
        val icon = typedArray.getResourceId(
            R.styleable.SnsLoginButton_loginButtonSnsLogo,
            R.drawable.ic_kakao,
        )
        val text = typedArray.getText(R.styleable.SnsLoginButton_loginButtonText)
        val textColor = typedArray.getColor(
            R.styleable.SnsLoginButton_loginButtonTextColor,
            ContextCompat.getColor(context, R.color.black01),
        )

        binding.apply {
            constraintLayoutButton.setBackgroundResource(background)
            progressBarLoadingButton.setIndicatorColor(
                ContextCompat.getColor(
                    context,
                    progressBarTint,
                ),
            )
            imageViewSnsLogo.setImageResource(icon)
            textViewContent.text = text
            textViewContent.setTextColor(textColor)
        }

        typedArray.recycle()
    }
}
