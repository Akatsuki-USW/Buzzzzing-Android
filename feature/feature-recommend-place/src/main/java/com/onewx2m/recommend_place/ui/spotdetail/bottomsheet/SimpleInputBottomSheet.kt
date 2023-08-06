package com.onewx2m.recommend_place.ui.spotdetail.bottomsheet

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.onewx2m.core_ui.extensions.onThrottleClick
import com.onewx2m.design_system.databinding.BottomSheetSimpleInputBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SimpleInputBottomSheet : BottomSheetDialogFragment() {

    private val binding: BottomSheetSimpleInputBinding by lazy {
        BottomSheetSimpleInputBinding.inflate(layoutInflater)
    }

    companion object {
        private const val TITLE = "TITLE"
        private const val CONTENT = "CONTENT"

        fun newInstance(
            title: String,
            content: String,
            onButtonClick: (String) -> Unit = {},
        ) = SimpleInputBottomSheet().apply {
            this.onButtonClick = onButtonClick
            arguments = Bundle().apply {
                putString(TITLE, title)
                putString(CONTENT, content)
            }
        }
    }

    private var onButtonClick: (String) -> Unit = {}
    private val content
        get() = arguments?.getString(CONTENT) ?: ""

    private val title
        get() = arguments?.getString(TITLE) ?: ""

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        return binding.root
    }

    private var behavior: BottomSheetBehavior<View>? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val bottomSheet =
            dialog?.findViewById<View>(com.google.android.material.R.id.design_bottom_sheet)
                ?: return

        behavior = BottomSheetBehavior.from<View>(bottomSheet).apply {
            isHideable = false
            isDraggable = false
            state = BottomSheetBehavior.STATE_EXPANDED
        }

        binding.apply {
            textViewTitle.text = title
            buttonMain.onThrottleClick {
                onButtonClick(editTextComment.editText.text.toString())
                dismiss()
            }

            editTextComment.editText.setText(content)
        }
    }
}
