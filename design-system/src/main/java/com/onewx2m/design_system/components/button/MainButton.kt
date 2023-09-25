package com.onewx2m.design_system.components.button

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shape
import com.onewx2m.core_ui.extensions.onThrottleClick
import com.onewx2m.design_system.R
import com.onewx2m.design_system.databinding.ButtonMainBinding
import com.onewx2m.design_system.theme.BLUE
import com.onewx2m.design_system.theme.BLUE_LIGHT
import com.onewx2m.design_system.theme.GRAY03
import com.onewx2m.design_system.theme.GRAY06
import com.onewx2m.design_system.theme.WHITE01

enum class MainButtonState(val btnColor: Color, val textColor: Color = WHITE01) {
    POSITIVE(
        btnColor = BLUE,
    ),
    NEGATIVE(
        btnColor = BLUE_LIGHT,
    ),
    LOADING(
        btnColor = BLUE_LIGHT,
    ),
    DISABLE(
        btnColor = GRAY06,
        textColor = GRAY03,
        ),
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

    var state: MainButtonState = MainButtonState.POSITIVE
        set(value) {
            field = value
            when (field) {
                MainButtonState.POSITIVE -> changeStateToPositive()
                MainButtonState.NEGATIVE -> changeStateToNegative()
                MainButtonState.LOADING -> changeStateToLoading()
                MainButtonState.DISABLE -> changeStateToDisable()
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
