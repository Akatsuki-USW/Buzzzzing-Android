package com.onewx2m.design_system.components.dialog

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.annotation.StringRes
import androidx.appcompat.app.AlertDialog
import com.onewx2m.design_system.R
import com.onewx2m.design_system.databinding.DialogCommonBinding

class CommonDialog constructor(private val context: Context) {

    private val builder: AlertDialog.Builder by lazy {
        AlertDialog.Builder(context).setView(binding.root)
    }

    private val binding: DialogCommonBinding by lazy {
        DialogCommonBinding.inflate(LayoutInflater.from(context))
    }

    private var dialog: AlertDialog? = null

    fun setTitle(@StringRes messageId: Int): CommonDialog {
        binding.textViewTitle.apply {
            text = context.getText(messageId)
        }
        return this
    }

    fun setTitle(message: CharSequence): CommonDialog {
        binding.textViewTitle.apply {
            text = message
        }
        return this
    }

    fun setDescription(@StringRes messageId: Int?): CommonDialog {
        binding.textViewDescription.apply {
            if (messageId == null) {
                visibility = View.GONE
            } else {
                visibility = View.VISIBLE
                text = context.getText(messageId)
            }
        }
        return this
    }

    fun setDescription(message: CharSequence): CommonDialog {
        binding.textViewDescription.apply {
            if (message.isEmpty()) {
                visibility = View.GONE
            } else {
                visibility = View.VISIBLE
                text = message
            }
        }
        return this
    }

    fun setGoneDescription(): CommonDialog {
        binding.textViewDescription.visibility = View.GONE
        return this
    }

    fun setPositiveButton(
        @StringRes textId: Int = R.string.word_confirm,
        onClickListener: (view: View) -> (Unit),
    ): CommonDialog {
        binding.buttonPositive.apply {
            text = context.getText(textId)
            setOnClickListener(onClickListener)
            dismiss()
        }
        return this
    }

    fun setPositiveButton(
        text: CharSequence,
        onClickListener: (view: View) -> (Unit),
    ): CommonDialog {
        binding.buttonPositive.apply {
            this.text = text
            setOnClickListener(onClickListener)
            dismiss()
        }
        return this
    }

    fun setNegativeButton(
        @StringRes textId: Int = R.string.word_cancel,
        onClickListener: (view: View) -> (Unit) = {},
    ): CommonDialog {
        binding.buttonNegative.apply {
            visibility = View.VISIBLE
            text = context.getText(textId)
            this.text = text
            setOnClickListener(onClickListener)
        }
        return this
    }

    fun setNegativeButton(
        text: CharSequence,
        onClickListener: (view: View) -> (Unit) = {},
    ): CommonDialog {
        binding.buttonNegative.apply {
            visibility = View.VISIBLE
            this.text = text
            setOnClickListener(onClickListener)
        }
        return this
    }

    fun create() {
        dialog = builder.create()
    }

    fun show() {
        dialog = builder.create()
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog?.window?.requestFeature(Window.FEATURE_NO_TITLE)
        dialog?.show()
    }

    fun dismiss() {
        val parentViewGroup = binding.root.parent as? ViewGroup
        parentViewGroup?.removeView(binding.root)
        dialog?.dismiss()
    }
}
