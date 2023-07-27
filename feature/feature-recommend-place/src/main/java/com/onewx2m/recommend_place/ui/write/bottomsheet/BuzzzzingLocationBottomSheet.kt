package com.onewx2m.recommend_place.ui.write.bottomsheet

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
import com.onewx2m.design_system.components.recyclerview.buzzzzingshort.BuzzzzingShortAdapter
import com.onewx2m.design_system.components.recyclerview.buzzzzingshort.BuzzzzingShortItem
import com.onewx2m.design_system.databinding.BottomSheetSearchLocationBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class BuzzzzingLocationBottomSheet : BottomSheetDialogFragment() {

    private val binding: BottomSheetSearchLocationBinding by lazy {
        BottomSheetSearchLocationBinding.inflate(layoutInflater)
    }

    private val viewModel: BuzzzzingLocationBottomSheetViewModel by viewModels()

    companion object {

        fun newInstance(
            onItemClick: (BuzzzzingShortItem) -> Unit = {},
        ) = BuzzzzingLocationBottomSheet().apply {
            this.onItemClick = onItemClick
        }
    }

    private val buzzzzingLocationAdapter: BuzzzzingShortAdapter by lazy {
        BuzzzzingShortAdapter {
            onItemClick(it)
            dismiss()
        }
    }

    private var onItemClick: (BuzzzzingShortItem) -> Unit = {}

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
            isHideable = true
            isDraggable = false
        }

        binding.apply {
            recyclerView.apply {
                adapter = buzzzzingLocationAdapter
                itemAnimator = null
                layoutManager = LinearLayoutManager(requireContext())
                infiniteScrolls {
                    getBuzzzzingLocation()
                }
            }

            getBuzzzzingLocation()

            editTextSearch.editText.setOnEditorActionListener { _, actionId, _ ->
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    getBuzzzzingLocation(
                        binding.editTextSearch.editText.text.toString(),
                        true,
                    )
                    binding.editTextSearch.editText.clearFocus()
                    binding.root.context.hideKeyboard(binding.editTextSearch.editText)
                    return@setOnEditorActionListener true
                }
                false
            }
        }
    }

    private fun getBuzzzzingLocation(query: String = "", needClear: Boolean = false) {
        viewLifecycleOwner.lifecycle.coroutineScope.launch {
            if (needClear) {
                binding.recyclerView.scrollToPosition(0)
                buzzzzingLocationAdapter.submitList(
                    emptyList(),
                )
            }

            buzzzzingLocationAdapter.submitList(
                viewModel.getBuzzzzingLocation(
                    query,
                    needClear,
                ),
            )
        }
    }
}
