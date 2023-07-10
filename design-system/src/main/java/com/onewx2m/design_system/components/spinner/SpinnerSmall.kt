package com.onewx2m.design_system.components.spinner

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import com.onewx2m.design_system.R
import com.onewx2m.design_system.databinding.SpinnerSmallBinding

class SpinnerSmall @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
) : ConstraintLayout(context, attrs, defStyleAttr) {
    private val binding: SpinnerSmallBinding

    val text
        get() = binding.textView

    init {
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        binding = SpinnerSmallBinding.inflate(inflater, this, true)

        val typedArray =
            context.obtainStyledAttributes(attrs, R.styleable.SpinnerSmall, defStyleAttr, 0)

        val text = typedArray.getText(R.styleable.SpinnerSmall_spinnerText)
        binding.textView.text = text

        typedArray.recycle()
    }
}
