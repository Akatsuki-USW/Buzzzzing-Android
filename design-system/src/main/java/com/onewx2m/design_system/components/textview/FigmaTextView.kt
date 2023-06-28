package com.onewx2m.design_system.components.textview

import android.content.Context
import android.util.AttributeSet
import android.widget.TextView
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.view.updatePadding
import com.onewx2m.core_ui.extensions.px
import com.onewx2m.design_system.R
import kotlin.math.roundToInt

class FigmaTextView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
) :
    AppCompatTextView(context, attrs, defStyleAttr) {

    init {
        val typedArray =
            context.obtainStyledAttributes(attrs, R.styleable.FigmaTextView, defStyleAttr, 0)

        val lineHeight = typedArray.getFloat(R.styleable.FigmaTextView_figmaLineHeight, 0f)
        this.syncLineHeight(lineHeight)

        typedArray.recycle()
    }
}

private fun TextView.syncLineHeight(figmaLineHeight: Float) {
    val lineSpacingExtra =
        figmaLineHeight.px - this.textSize

    val padding = lineSpacingExtra
        .takeIf { it != 0.0f }
        ?.div(2)
        ?.roundToInt()
        ?: 0

    setLineSpacing(padding.toFloat(), 1f)
    updatePadding(
        top = padding,
        bottom = padding,
    )
}
