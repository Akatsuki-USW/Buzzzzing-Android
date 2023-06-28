package com.onewx2m.design_system.components.button

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import com.onewx2m.core_ui.extensions.onThrottleClick
import com.onewx2m.design_system.R
import com.onewx2m.design_system.databinding.ButtonSnsLoginBinding

sealed interface SnsLoginButtonState {
    object Enable : SnsLoginButtonState
    object Loading : SnsLoginButtonState
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

    var state: SnsLoginButtonState = SnsLoginButtonState.Enable
        set(value) {
            field = value
            when (field) {
                SnsLoginButtonState.Enable -> {
                    changeStateToEnable()
                }

                SnsLoginButtonState.Loading -> {
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
        binding = DataBindingUtil.inflate(inflater, R.layout.button_sns_login, this, true)

        val typedArray =
            context.obtainStyledAttributes(attrs, R.styleable.SnsLoginButton, defStyleAttr, 0)

        val background = typedArray.getResourceId(
            R.styleable.SnsLoginButton_loginButtonBackground,
            R.drawable.ripple_bg_solid_kakao01_rounded_5,
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
