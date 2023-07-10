package com.onewx2m.design_system.components.recyclerview.buzzzzingmedium

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.LinearLayoutManager
import com.onewx2m.core_ui.extensions.infiniteScrolls
import com.onewx2m.design_system.databinding.RecyclerViewBuzzzzingMediumBinding
import timber.log.Timber

class BuzzzzingMediumRecyclerView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
) : ConstraintLayout(context, attrs, defStyleAttr) {
    private val binding: RecyclerViewBuzzzzingMediumBinding
    private var buzzzzingMediumAdapter: BuzzzzingMediumAdapter? = null

    fun submitList(data: List<BuzzzzingMediumItem>) {
        buzzzzingMediumAdapter?.submitList(data)
    }

    fun initAdapter(
        onItemClick: (Int) -> Unit = {},
        onBookmarkClick: (Int) -> Unit = {},
        infiniteScrolls: () -> Unit = {},
    ) {
        if (buzzzzingMediumAdapter != null) return
        buzzzzingMediumAdapter = BuzzzzingMediumAdapter(onItemClick, onBookmarkClick)

        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            adapter = buzzzzingMediumAdapter
            infiniteScrolls {
                infiniteScrolls()
            }
        }
    }

    init {
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        binding = RecyclerViewBuzzzzingMediumBinding.inflate(inflater, this, true)
    }
}
