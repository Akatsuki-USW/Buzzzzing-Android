package com.onewx2m.design_system.components.button

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.onewx2m.core_ui.extensions.onThrottleClick
import com.onewx2m.design_system.R
import com.onewx2m.design_system.databinding.ButtonMainBinding
import com.onewx2m.design_system.modifier.buzzzzingClickable
import com.onewx2m.design_system.theme.BLUE
import com.onewx2m.design_system.theme.BLUE_LIGHT
import com.onewx2m.design_system.theme.BuzzzzingTheme
import com.onewx2m.design_system.theme.GRAY03
import com.onewx2m.design_system.theme.GRAY06
import com.onewx2m.design_system.theme.WHITE01
import com.onewx2m.design_system.util.runIf

enum class MainButtonType(val backgroundColor: Color, val textColor: Color = WHITE01) {
    POSITIVE(
        backgroundColor = BLUE,
    ),
    NEGATIVE(
        backgroundColor = BLUE_LIGHT,
    ),
    LOADING(
        backgroundColor = BLUE_LIGHT,
    ),
    DISABLE(
        backgroundColor = GRAY06,
        textColor = GRAY03,
    ),
}

@Composable
fun MainButton(
    modifier: Modifier = Modifier,
    type: MainButtonType = MainButtonType.POSITIVE,
    rippleColor: Color = Color.Unspecified,
    text: String,
    onClick: () -> Unit = {},
) {
    val isLoading = type == MainButtonType.LOADING
    val isClickable = type in listOf(MainButtonType.POSITIVE, MainButtonType.NEGATIVE)

    Box(
        modifier = modifier
            .background(
                color = type.backgroundColor,
                shape = RoundedCornerShape(5.dp),
            )
            .fillMaxWidth()
            .height(50.dp)
            .runIf(isClickable) {
                buzzzzingClickable(
                    rippleEnabled = true,
                    rippleColor = rippleColor,
                    onClick = onClick,
                )
            },
    ) {
        Row(
            modifier = Modifier
                .fillMaxHeight()
                .padding(horizontal = 24.dp)
                .align(Alignment.Center),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            if (isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier.size(20.dp),
                    strokeWidth = 3.dp,
                    color = type.textColor,
                    trackColor = Color.Transparent,
                )
                Spacer(modifier = Modifier.width(10.dp))
            }
            Text(
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
fun MainButtonPreview() {
    BuzzzzingTheme {
        Column {
            MainButton(text = "메인 버튼", type = MainButtonType.POSITIVE)
            Spacer(modifier = Modifier.size(10.dp))
            MainButton(text = "메인 버튼", type = MainButtonType.NEGATIVE)
            Spacer(modifier = Modifier.size(10.dp))
            MainButton(text = "메인 버튼", type = MainButtonType.DISABLE)
            Spacer(modifier = Modifier.size(10.dp))
            MainButton(text = "메인 버튼", type = MainButtonType.LOADING)
        }
    }
}

class MainButton @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
) :
    FrameLayout(context, attrs, defStyleAttr) {

    fun onThrottleClick(onClick: () -> Unit) =
        binding.constraintLayoutButton.onThrottleClick {
            onClick()
        }

    private val binding: ButtonMainBinding

    var text: String = ""
        set(value) {
            field = value
            binding.textViewContent.text = field
        }

    var state: MainButtonType = MainButtonType.POSITIVE
        set(value) {
            field = value
            when (field) {
                MainButtonType.POSITIVE -> changeStateToPositive()
                MainButtonType.NEGATIVE -> changeStateToNegative()
                MainButtonType.LOADING -> changeStateToLoading()
                MainButtonType.DISABLE -> changeStateToDisable()
            }
        }

    private fun changeStateToPositive() {
        setButtonClickFocus(true)
        binding.apply {
            constraintLayoutButton.setBackgroundResource(R.drawable.ripple_black01_bg_solid_blue_rounded_5)
            textViewContent.setTextColor(context.getColor(R.color.white01))
        }
        hideLoading()
    }

    private fun changeStateToNegative() {
        setButtonClickFocus(true)
        binding.apply {
            constraintLayoutButton.setBackgroundResource(R.drawable.ripple_gray01_bg_solid_blue_light_rounded_5)
            textViewContent.setTextColor(context.getColor(R.color.white01))
        }
        hideLoading()
    }

    private fun changeStateToLoading() {
        setButtonClickFocus(false)
        binding.apply {
            progressBarLoadingButton.visibility = View.VISIBLE
            viewLoading.visibility = View.VISIBLE
        }
    }

    private fun changeStateToDisable() {
        setButtonClickFocus(false)
        binding.apply {
            constraintLayoutButton.setBackgroundResource(R.drawable.bg_solid_gray06_rounded_5)
            textViewContent.setTextColor(context.getColor(R.color.gray03))
        }
        hideLoading()
    }

    private fun setButtonClickFocus(state: Boolean) {
        binding.constraintLayoutButton.apply {
            isClickable = state
            isFocusable = state
        }
    }

    private fun hideLoading() {
        binding.apply {
            progressBarLoadingButton.visibility = View.GONE
            viewLoading.visibility = View.GONE
        }
    }

    init {
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        binding = ButtonMainBinding.inflate(inflater, this, true)

        val typedArray =
            context.obtainStyledAttributes(attrs, R.styleable.MainButton, defStyleAttr, 0)

        val text = typedArray.getText(R.styleable.MainButton_mainButtonText)
        binding.textViewContent.text = text

        typedArray.recycle()
    }
}
