package com.onewx2m.design_system.components.textinputlayout

import android.content.Context
import android.text.InputFilter
import android.text.InputType
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import androidx.annotation.ColorRes
import androidx.constraintlayout.widget.ConstraintLayout
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

    companion object {
        private const val LOSING_FOCUS_DELAY = 250L
    }

    private val binding: TextInputLayoutBinding

    var helperText: String = ""
        set(value) {
            field = value
            binding.helperText.text = field
        }

    val editText
        get() = binding.editText

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
        changeLabelUnderlineHelperTextColor(R.color.mint)
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
            if (isPreventLosingFocus) return@setOnFocusChangeListener
            if (hasFocus) preventFocusClearedByAdjustResize()
            if (!hasFocus && state == TextInputLayoutState.FOCUSED) {
                state =
                    TextInputLayoutState.INACTIVE
            }
            if (hasFocus && state == TextInputLayoutState.INACTIVE) {
                state =
                    TextInputLayoutState.FOCUSED
            }
        }

        val typedArray =
            context.obtainStyledAttributes(attrs, R.styleable.TextInputLayout, defStyleAttr, 0)

        val label = typedArray.getText(R.styleable.TextInputLayout_label)
        val isHideLabel = typedArray.getBoolean(R.styleable.TextInputLayout_hideLabel, false)

        val helperText = typedArray.getText(R.styleable.TextInputLayout_helperTextContent)
        val isHideHelperText =
            typedArray.getBoolean(R.styleable.TextInputLayout_hideHelperTextContent, false)

        val hint = typedArray.getText(R.styleable.TextInputLayout_textInputLayoutHint)

        val line = typedArray.getInteger(R.styleable.TextInputLayout_textInputEditTextLine, -1)
        val maxLength = typedArray.getInteger(R.styleable.TextInputLayout_textInputMaxLength, 50)

        if (line > 0) binding.editText.setLines(line)
        if(line == 1) binding.editText.inputType = InputType.TYPE_CLASS_TEXT

        binding.editText.filters = arrayOf(InputFilter.LengthFilter(maxLength))

        binding.label.text = label
        binding.label.visibility = if (isHideLabel) View.GONE else View.VISIBLE

        binding.helperText.text = helperText
        binding.helperText.visibility = if (isHideHelperText) View.GONE else View.VISIBLE

        binding.editText.hint = hint

        typedArray.recycle()
    }

    private var isPreventLosingFocus = false

    private fun preventFocusClearedByAdjustResize() {
        isPreventLosingFocus = true

        binding.editText.postDelayed({
            binding.editText.run {
                if (isFocused.not()) {
                    requestFocus()
                }
            }
            isPreventLosingFocus = false
        }, LOSING_FOCUS_DELAY)
    }
}
