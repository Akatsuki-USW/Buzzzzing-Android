package com.onewx2m.design_system.components.textinputlayout

import android.content.Context
import android.text.InputFilter
import android.text.InputType
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import androidx.annotation.ColorRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.widget.ConstraintLayout
import com.onewx2m.design_system.R
import com.onewx2m.design_system.databinding.TextInputLayoutBinding
import com.onewx2m.design_system.theme.BLUE
import com.onewx2m.design_system.theme.BuzzzzingTheme
import com.onewx2m.design_system.theme.GRAY01
import com.onewx2m.design_system.theme.GRAY05
import com.onewx2m.design_system.theme.MINT
import com.onewx2m.design_system.theme.PINK

enum class TextInputLayoutState(val color: Color) {
    INACTIVE(GRAY05), FOCUSED(GRAY01), ERROR(PINK), LOADING(MINT), SUCCESS(BLUE)
}

@Composable
fun BuzzTextField(
    modifier: Modifier = Modifier,
    label: String = "",
    text: String = "",
    hint: String = "",
    helperText: String = "",
    initType: TextInputLayoutState = TextInputLayoutState.FOCUSED,
    maxLines: Int = 1,
    minLines: Int = 1,
    onTextChange: (String) -> Unit = {},
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isFocused by interactionSource.collectIsFocusedAsState()

    val type = when {
        !isFocused && initType == TextInputLayoutState.FOCUSED -> {
            TextInputLayoutState.INACTIVE
        }

        isFocused && initType == TextInputLayoutState.INACTIVE -> {
            TextInputLayoutState.FOCUSED
        }

        else -> initType
    }

    BasicTextField(
        modifier = modifier.fillMaxWidth(),
        interactionSource = interactionSource,
        value = text,
        onValueChange = onTextChange,
        decorationBox = {
            Column {
                if (label.isNotEmpty()) {
                    Text(
                        style = BuzzzzingTheme.typography.label2,
                        text = label,
                        color = type.color,
                    )
                }

                Spacer(modifier = Modifier.size(6.dp))
                Row(
                    modifier = Modifier.padding(
                        bottom = 6.dp,
                    ).fillMaxWidth(),
                    Arrangement.SpaceBetween,
                ) {
                    if (text.isNotEmpty()) {
                        Text(
                            style = BuzzzzingTheme.typography.body1,
                            text = text,
                            color = GRAY01,
                            maxLines = if (minLines > maxLines) minLines else maxLines,
                            minLines = minLines,
                        )
                    } else {
                        Text(style = BuzzzzingTheme.typography.body1, text = hint, color = GRAY05)
                    }

                    when (type) {
                        TextInputLayoutState.INACTIVE, TextInputLayoutState.FOCUSED -> Unit
                        TextInputLayoutState.ERROR -> {
                            Image(
                                modifier = Modifier.padding(end = 4.dp),
                                painter = painterResource(
                                    id = R.drawable.ic_text_input_layor_error,
                                ),
                                contentDescription = "",
                            )
                        }

                        TextInputLayoutState.LOADING -> {
                            CircularProgressIndicator(
                                modifier = Modifier.size(20.dp).padding(end = 4.dp),
                                strokeWidth = 2.dp,
                                color = MINT,
                                trackColor = Color.Transparent,
                            )
                        }

                        TextInputLayoutState.SUCCESS -> {
                            Image(
                                modifier = Modifier.padding(end = 4.dp),
                                painter = painterResource(
                                    id = R.drawable.ic_text_input_layor_success,
                                ),
                                contentDescription = "",
                            )
                        }
                    }
                }
                Spacer(
                    modifier = Modifier.fillMaxWidth().height(1.dp).background(color = type.color),
                )
                Spacer(modifier = Modifier.height(4.dp))

                if (helperText.isNotEmpty()) {
                    Text(
                        style = BuzzzzingTheme.typography.label3,
                        text = helperText,
                        color = type.color,
                    )
                }
            }
        },
    )
}

@Preview(showBackground = true)
@Composable
fun BuzzTextFieldPreview() {
    BuzzzzingTheme {
        Column {
            BuzzTextField(
                label = "라벨",
                text = "text",
                hint = "힌트",
                helperText = "헬퍼 텍스트",
                initType = TextInputLayoutState.FOCUSED,
                onTextChange = { },
            )
            Spacer(modifier = Modifier.size(20.dp))

            BuzzTextField(
                label = "라벨",
                text = "",
                hint = "힌트",
                helperText = "헬퍼 텍스트",
                initType = TextInputLayoutState.INACTIVE,
                onTextChange = { },
            )
            Spacer(modifier = Modifier.size(20.dp))

            BuzzTextField(
                label = "라벨",
                text = "text",
                hint = "힌트",
                helperText = "헬퍼 텍스트",
                initType = TextInputLayoutState.LOADING,
                onTextChange = { },
            )
            Spacer(modifier = Modifier.size(20.dp))

            BuzzTextField(
                label = "라벨",
                text = "text",
                hint = "힌트",
                helperText = "헬퍼 텍스트",
                initType = TextInputLayoutState.ERROR,
                onTextChange = { },
            )
            Spacer(modifier = Modifier.size(20.dp))

            BuzzTextField(
                label = "라벨",
                text = "text",
                hint = "힌트",
                helperText = "헬퍼 텍스트",
                initType = TextInputLayoutState.SUCCESS,
                minLines = 5,
                onTextChange = { },
            )
        }
    }
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
        if (line == 1) binding.editText.inputType = InputType.TYPE_CLASS_TEXT

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
