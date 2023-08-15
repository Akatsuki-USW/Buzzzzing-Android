package com.onewx2m.feature_myinfo.ui.ban

import android.os.Bundle
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.onewx2m.core_ui.extensions.onThrottleClick
import com.onewx2m.core_ui.util.DeepLinkUtil
import com.onewx2m.design_system.components.recyclerview.ban.BanAdapter
import com.onewx2m.design_system.components.toast.ErrorToast
import com.onewx2m.feature_myinfo.databinding.FragmentBanBinding
import com.onewx2m.mvi.MviFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BanFragment :
    MviFragment<FragmentBanBinding, BanViewState, BanEvent, BanSideEffect, BanViewModel>(
        FragmentBanBinding::inflate,
    ) {
    override val viewModel: BanViewModel by viewModels()

    private val listAdapter: BanAdapter by lazy {
        BanAdapter()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel.getBanList()
    }

    override fun initView() {
        binding.apply {
            recyclerView.apply {
                layoutManager = LinearLayoutManager(requireContext())
                adapter = listAdapter
            }

            imageViewBack.onThrottleClick {
                viewModel.popBackStack()
            }
        }
    }

    override fun render(current: BanViewState) {
        super.render(current)

        listAdapter.submitList(current.banList)
    }

    override fun handleSideEffect(sideEffect: BanSideEffect) {
        super.handleSideEffect(sideEffect)

        when (sideEffect) {
            BanSideEffect.GoToLoginFragment -> goToLoginFragment()
            BanSideEffect.PopBackStack -> findNavController().popBackStack()
            is BanSideEffect.ShowErrorToast -> ErrorToast.make(
                binding.root,
                sideEffect.msg,
            ).show()
        }
    }

    private fun goToLoginFragment() {
        val (request, navOptions) = DeepLinkUtil.getLoginRequestAndOption(
            requireContext(),
            findNavController().graph.id,
            true,
        )
        findNavController().navigate(request, navOptions)
    }
}
