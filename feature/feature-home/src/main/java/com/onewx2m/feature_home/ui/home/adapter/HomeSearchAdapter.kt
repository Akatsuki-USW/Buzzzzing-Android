package com.onewx2m.feature_home.ui.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.recyclerview.widget.RecyclerView
import com.onewx2m.core_ui.extensions.hideKeyboard
import com.onewx2m.core_ui.extensions.onThrottleClick
import com.onewx2m.feature_home.databinding.ItemRecyclerViewHomeSearchBinding

class HomeSearchAdapter(
    private val onCongestionFilterClick: () -> Unit = {},
    private val onLocationFilterClick: () -> Unit = {},
    private val onSearch: (String) -> Unit = {},
) :
    RecyclerView.Adapter<HomeSearchHolder>() {
    var congestionLevelSpinnerText: String = ""
    var locationSpinnerText: String = ""
    var keyword: String? = ""

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeSearchHolder {
        val binding =
            ItemRecyclerViewHomeSearchBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false,
            )
        return HomeSearchHolder(
            binding = binding,
            onCongestionFilterClick = onCongestionFilterClick,
            onLocationFilterClick = onLocationFilterClick,
            onSearch = onSearch,
        )
    }

    override fun onBindViewHolder(holder: HomeSearchHolder, position: Int) {
        holder.bind(keyword, congestionLevelSpinnerText, locationSpinnerText)
    }

    override fun getItemCount(): Int = 1
}

class HomeSearchHolder(
    private val binding: ItemRecyclerViewHomeSearchBinding,
    private val onSearch: (String) -> Unit = {},
    private val onCongestionFilterClick: () -> Unit = {},
    private val onLocationFilterClick: () -> Unit = {},
) :
    RecyclerView.ViewHolder(binding.root) {

    init {
        binding.editTextSearch.editText.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                onSearch(binding.editTextSearch.editText.text.toString())
                binding.editTextSearch.editText.clearFocus()
                binding.root.context.hideKeyboard(binding.editTextSearch.editText)
                return@setOnEditorActionListener true
            }
            false
        }

        binding.spinnerSmallLocation.onThrottleClick {
            onLocationFilterClick()
        }

        binding.spinnerSmallCongestion.onThrottleClick {
            onCongestionFilterClick()
        }
    }

    fun bind(
        keyword: String? = "",
        congestionLevelSpinnerText: String = "",
        locationSpinnerText: String = "",
    ) {
        binding.editTextSearch.editText.setText(keyword)
        binding.spinnerSmallCongestion.text = congestionLevelSpinnerText
        binding.spinnerSmallLocation.text = locationSpinnerText
    }
}
