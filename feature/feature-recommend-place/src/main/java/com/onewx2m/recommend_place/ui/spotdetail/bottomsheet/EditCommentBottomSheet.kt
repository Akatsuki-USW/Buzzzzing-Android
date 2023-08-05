package com.onewx2m.recommend_place.ui.spotdetail.bottomsheet

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.fragment.app.viewModels
import androidx.lifecycle.coroutineScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.onewx2m.core_ui.extensions.hideKeyboard
import com.onewx2m.core_ui.extensions.infiniteScrolls
import com.onewx2m.core_ui.extensions.onThrottleClick
import com.onewx2m.design_system.components.recyclerview.buzzzzingshort.BuzzzzingShortAdapter
import com.onewx2m.design_system.components.recyclerview.buzzzzingshort.BuzzzzingShortItem
import com.onewx2m.design_system.databinding.BottomSheetEditCommentBinding
import com.onewx2m.design_system.databinding.BottomSheetSearchLocationBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class EditCommentBottomSheet : BottomSheetDialogFragment() {

    private val binding: BottomSheetEditCommentBinding by lazy {
        BottomSheetEditCommentBinding.inflate(layoutInflater)
    }

    companion object {
        private const val CONTENT = "CONTENT"

        fun newInstance(
            content: String,
            onButtonClick: (String) -> Unit = {},
        ) = EditCommentBottomSheet().apply {
            this.onButtonClick = onButtonClick
            arguments = Bundle().apply {
                putString(CONTENT, content)
            }
        }
    }

    private var onButtonClick: (String) -> Unit = {}
    private val content
        get() = arguments?.getString(CONTENT) ?: ""

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
            buttonMain.onThrottleClick {
                onButtonClick(editTextComment.editText.text.toString())
                dismiss()
            }

            editTextComment.editText.setText(content)
        }
    }
}
