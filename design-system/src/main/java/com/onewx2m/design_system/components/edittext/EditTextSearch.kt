package com.onewx2m.design_system.components.edittext

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import com.onewx2m.design_system.R
import com.onewx2m.design_system.databinding.EditTextSearchBinding

class EditTextSearch @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
) : ConstraintLayout(context, attrs, defStyleAttr) {
    private val binding: EditTextSearchBinding

    val editText
        get() = binding.editText

    init {
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        binding = EditTextSearchBinding.inflate(inflater, this, true)

        binding.editText.setOnFocusChangeListener { _, hasFocus ->
            if (isPreventLosingFocus) return@setOnFocusChangeListener
            if (hasFocus) {
                preventFocusClearedByAdjustResize()
            }
        }

        val typedArray =
            context.obtainStyledAttributes(attrs, R.styleable.EditTextSearch, defStyleAttr, 0)

        val hint = typedArray.getText(R.styleable.EditTextSearch_editTextSearchHint)

        binding.editText.hint = hint

        typedArray.recycle()
    }

    private var isPreventLosingFocus = false

    private fun preventFocusClearedByAdjustResize() {
        isPreventLosingFocus = true
        val losingFocusDelay = 250L

        binding.editText.postDelayed({
            binding.editText.run {
                if (isFocused.not()) {
                    requestFocus()
                }
            }
            isPreventLosingFocus = false
        }, losingFocusDelay)
    }
}
