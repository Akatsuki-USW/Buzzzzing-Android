package com.onewx2m.design_system.components.button

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import com.onewx2m.core_ui.extensions.onThrottleClick
import com.onewx2m.design_system.R
import com.onewx2m.design_system.databinding.ButtonMainBinding

enum class MainButtonState {
    POSITIVE, NEGATIVE, LOADING, DISABLE
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
            constraintLayoutButton.setBackgroundResource(R.drawable.ripple_gray01_bg_solid_blue_rounded_5)
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
            progressBarLoadingButton.visibility = View.INVISIBLE
            viewLoading.visibility = View.GONE
        }
    }

    init {
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        binding = ButtonMainBinding.inflate(inflater, this, true)
    }
}
