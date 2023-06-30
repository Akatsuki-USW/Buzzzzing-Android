package com.onewx2m.design_system.components.textinputlayout

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import androidx.annotation.ColorRes
import androidx.constraintlayout.widget.ConstraintLayout
import com.onewx2m.core_ui.extensions.afterTextChanged
import com.onewx2m.design_system.R
import com.onewx2m.design_system.databinding.TextInputLayoutBinding

enum class TextInputLayoutState {
    INACTIVE, FOCUSED, ERROR, LOADING, SUCCESS
}

class TextInputLayout @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
) : ConstraintLayout(context, attrs, defStyleAttr) {
    private val binding: TextInputLayoutBinding

    var helperText: String = ""
        set(value) {
            field = value
            binding.helperText.text = field
        }

    val text: String
        get() = binding.editText.text.toString()

    fun afterTextChanged(method: (text: String) -> Unit) {
        binding.editText.afterTextChanged {
            method(it.toString())
        }
    }

    var state: TextInputLayoutState = TextInputLayoutState.INACTIVE
        set(value) {
            field = value
            when (field) {
                TextInputLayoutState.INACTIVE -> changeStateToInactive()
                TextInputLayoutState.FOCUSED -> changeStateToFocused()
                TextInputLayoutState.ERROR -> changeStateToError()
                TextInputLayoutState.LOADING -> changeStateToLoading()
                TextInputLayoutState.SUCCESS -> changeStateToSuccess()
            }
        }

    private fun changeStateToInactive() {
        changeLabelUnderlineHelperTextColor(R.color.gray05)
        binding.apply {
            imageViewError.visibility = View.INVISIBLE
            imageViewSuccess.visibility = View.INVISIBLE
            progressBar.visibility = View.INVISIBLE
        }
    }

    private fun changeStateToFocused() {
        changeLabelUnderlineHelperTextColor(R.color.gray01)
        binding.apply {
            imageViewError.visibility = View.INVISIBLE
            imageViewSuccess.visibility = View.INVISIBLE
            progressBar.visibility = View.INVISIBLE
        }
    }

    private fun changeStateToError() {
        changeLabelUnderlineHelperTextColor(R.color.pink)
        binding.apply {
            imageViewError.visibility = View.VISIBLE
            imageViewSuccess.visibility = View.INVISIBLE
            progressBar.visibility = View.INVISIBLE
        }
    }

    private fun changeStateToLoading() {
        changeLabelUnderlineHelperTextColor(R.color.gray01)
        binding.apply {
            imageViewError.visibility = View.INVISIBLE
            imageViewSuccess.visibility = View.INVISIBLE
            progressBar.visibility = View.VISIBLE
        }
    }

    private fun changeStateToSuccess() {
        changeLabelUnderlineHelperTextColor(R.color.blue)
        binding.apply {
            imageViewError.visibility = View.INVISIBLE
            imageViewSuccess.visibility = View.VISIBLE
            progressBar.visibility = View.INVISIBLE
        }
    }

    private fun changeLabelUnderlineHelperTextColor(@ColorRes colorId: Int) {
        binding.apply {
            val colorInt = context.getColor(colorId)
            label.setTextColor(colorInt)
            underline.setBackgroundColor(colorInt)
            helperText.setTextColor(colorInt)
        }
    }

    init {
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        binding = TextInputLayoutBinding.inflate(inflater, this, true)

        binding.editText.setOnFocusChangeListener { _, hasFocus ->
            if (!hasFocus && state == TextInputLayoutState.FOCUSED) changeStateToInactive()
            if (hasFocus && state == TextInputLayoutState.INACTIVE) changeStateToFocused()
        }
    }
}
