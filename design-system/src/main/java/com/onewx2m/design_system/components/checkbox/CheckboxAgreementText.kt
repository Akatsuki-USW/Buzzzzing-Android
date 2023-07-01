package com.onewx2m.design_system.components.checkbox

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import com.onewx2m.core_ui.extensions.onThrottleClick
import com.onewx2m.design_system.R
import com.onewx2m.design_system.databinding.CheckboxAgreementTextBinding

class CheckboxAgreementText @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
) : ConstraintLayout(context, attrs, defStyleAttr) {
    private val binding: CheckboxAgreementTextBinding

    fun onThrottleClick(onClick: () -> Unit) =
        binding.root.onThrottleClick {
            onClick()
        }

    fun checkboxChangeListener(onChange: (Boolean) -> Unit) = binding.checkBox.setOnCheckedChangeListener { _, isChecked ->
        onChange(isChecked)
    }

    init {
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        binding = CheckboxAgreementTextBinding.inflate(inflater, this, true)

        val typedArray =
            context.obtainStyledAttributes(
                attrs,
                R.styleable.CheckboxAgreementText,
                defStyleAttr,
                0,
            )

        val text =
            typedArray.getText(R.styleable.CheckboxAgreementText_checkboxAgreementTextContent)
        binding.textViewContent.text = text

        val isHideArrow =
            typedArray.getBoolean(R.styleable.CheckboxAgreementText_hideRightArrow, false)
        binding.imageViewArrow.visibility = if (isHideArrow) View.INVISIBLE else View.VISIBLE

        typedArray.recycle()
    }
}
