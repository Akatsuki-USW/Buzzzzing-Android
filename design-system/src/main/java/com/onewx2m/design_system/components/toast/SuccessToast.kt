package com.onewx2m.design_system.components.toast

import android.view.LayoutInflater
import android.view.View
import androidx.core.content.ContextCompat
import com.google.android.material.snackbar.Snackbar
import com.onewx2m.design_system.R
import com.onewx2m.design_system.databinding.ToastSuccessBinding

class SuccessToast(view: View, private val message: String) {

    companion object {
        private const val SNACK_BAR_DURATION = 2000

        fun make(view: View, message: String) = SuccessToast(view, message)
    }

    private val context = view.context
    private val snackbar = Snackbar.make(view, "", SNACK_BAR_DURATION)
    private val snackbarLayout = snackbar.view as Snackbar.SnackbarLayout

    private val inflater = LayoutInflater.from(context)
    private val snackbarBinding: ToastSuccessBinding =
        ToastSuccessBinding.inflate(inflater, null, false)

    init {
        initView()
        initData()
    }

    private fun initView() {
        with(snackbarLayout) {
            removeAllViews()
            setPadding(0, 0, 0, 0)
            setBackgroundColor(ContextCompat.getColor(context, android.R.color.transparent))
            addView(snackbarBinding.root, 0)
        }
    }

    private fun initData() {
        snackbarBinding.textViewToastMessage.text = message
    }

    fun show() {
        snackbar.show()
    }
}
