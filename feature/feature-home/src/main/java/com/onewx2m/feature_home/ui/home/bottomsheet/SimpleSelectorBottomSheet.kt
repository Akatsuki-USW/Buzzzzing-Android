package com.onewx2m.feature_home.ui.home.bottomsheet

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.onewx2m.design_system.components.recyclerview.simpleselector.SimpleSelectorAdapter
import com.onewx2m.design_system.databinding.BottomSheetSimpleSelectorBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SimpleSelectorBottomSheet : BottomSheetDialogFragment() {

    private val binding: BottomSheetSimpleSelectorBinding by lazy {
        BottomSheetSimpleSelectorBinding.inflate(layoutInflater)
    }

    companion object {
        private const val ALREADY_SELECTED_DATA = "ALREADY_SELECTED_DATA"
        private const val DATA_LIST = "DATA_LIST"

        fun newInstance(
            alreadySelectedData: String,
            dataList: List<String>,
            onItemClick: (String) -> Unit = {},
        ) = SimpleSelectorBottomSheet().apply {
            this.onItemClick = onItemClick
            arguments = Bundle().apply {
                putString(ALREADY_SELECTED_DATA, alreadySelectedData)
                putStringArray(DATA_LIST, dataList.toTypedArray())
            }
        }
    }

    private val alreadySelectedData
        get() = arguments?.getString(ALREADY_SELECTED_DATA, "") ?: ""
    private val dataList
        get() = arguments?.getStringArray(DATA_LIST) ?: emptyArray()

    private val simpleSelectorAdapter: SimpleSelectorAdapter by lazy {
        SimpleSelectorAdapter(alreadySelectedData, dataList.toList()) {
            onItemClick(it)
            dismiss()
        }
    }

    private var onItemClick: (String) -> Unit = {}

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
                adapter = simpleSelectorAdapter
                layoutManager = LinearLayoutManager(requireContext())
            }
        }
    }
}
