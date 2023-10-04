package com.onewx2m.design_system.components.checkbox

import android.content.Context
import android.graphics.drawable.Icon
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.widget.ConstraintLayout
import com.onewx2m.core_ui.extensions.onThrottleClick
import com.onewx2m.design_system.R
import com.onewx2m.design_system.databinding.CheckboxAgreementTextBinding
import com.onewx2m.design_system.modifier.buzzzzingClickable
import com.onewx2m.design_system.theme.BLUE
import com.onewx2m.design_system.theme.BLUE_LIGHT
import com.onewx2m.design_system.theme.BuzzzzingTheme

@Composable
fun CheckboxAgreementText(
    text: String = "",
    checked: Boolean,
    onTextClick: () -> Unit = {},
    onCheckedChange: (Boolean) -> Unit = {},
) {
    Box(
        modifier = Modifier.fillMaxWidth(),
    ) {
        Row(
            modifier = Modifier
                .align(Alignment.CenterStart)
                .buzzzzingClickable { onTextClick() },
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(text = text, style = BuzzzzingTheme.typography.body1)
            Spacer(modifier = Modifier.width(4.dp))
            Icon(
                painter = painterResource(
                    id = R.drawable.ic_arrow_right,
                ),
                contentDescription = "",
            )
        }

        Icon(
            modifier = Modifier.align(Alignment.CenterEnd).buzzzzingClickable {
                onCheckedChange(!checked)
            },
            painter = painterResource(
                id = R.drawable.ic_check_active,
            ),
            contentDescription = "",
            tint = if (checked) BLUE else BLUE_LIGHT,
        )
    }
}

@Preview(showBackground = true)
@Composable
fun CheckboxAgreementTextPreview() {
    BuzzzzingTheme {
        CheckboxAgreementText(
            text = "개인정보 처리 방침",
            checked = true,
        )
    }
}

class CheckboxAgreementText @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
) : ConstraintLayout(context, attrs, defStyleAttr) {
    private val binding: CheckboxAgreementTextBinding

    var isChecked: Boolean = false
        set(value) {
            field = value
            binding.checkBox.isChecked = field
        }

    fun onThrottleClick(onClick: () -> Unit) =
        binding.root.onThrottleClick {
            onClick()
        }

    fun checkboxChangeListener(onChange: (Boolean) -> Unit) =
        binding.checkBox.setOnCheckedChangeListener { _, isChecked ->
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
