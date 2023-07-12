package com.onewx2m.feature_myinfo.ui.myinfo

import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.onewx2m.core_ui.extensions.loadProfileUrl
import com.onewx2m.core_ui.extensions.onThrottleClick
import com.onewx2m.design_system.components.toast.ErrorToast
import com.onewx2m.feature_myinfo.R
import com.onewx2m.feature_myinfo.databinding.FragmentMyInfoBinding
import com.onewx2m.feature_myinfo.ui.myinfo.adapter.MenuAdapter
import com.onewx2m.feature_myinfo.ui.myinfo.adapter.MyInfoMenu
import com.onewx2m.mvi.MviFragment

class MyInfoFragment :
    MviFragment<FragmentMyInfoBinding, MyInfoViewState, MyInfoEvent, MyInfoSideEffect, MyInfoViewModel>(
        FragmentMyInfoBinding::inflate,
    ) {
    companion object {
        private const val PROFILE_RADIUS = 8
    }

    override val viewModel: MyInfoViewModel by viewModels()
    private val listAdapter by lazy {
        MenuAdapter(::onMenuClick)
    }

    override fun initView() {
        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = listAdapter
        }
        listAdapter.submitList(MyInfoMenu.values().toList())

        binding.constraintLayoutEdit.onThrottleClick {
            viewModel.goToEdit()
        }
    }

    private fun onMenuClick(item: MyInfoMenu) = when (item) {
        MyInfoMenu.MY_ARTICLE -> ErrorToast.make(
            binding.root,
            requireContext().getString(item.StringRes),
        ).show()

        MyInfoMenu.ASK -> ErrorToast.make(binding.root, requireContext().getString(item.StringRes))
            .show()

        MyInfoMenu.BAN_HISTORY -> ErrorToast.make(
            binding.root,
            requireContext().getString(item.StringRes),
        ).show()

        MyInfoMenu.OPEN_SOURCE -> ErrorToast.make(
            binding.root,
            requireContext().getString(item.StringRes),
        ).show()

        MyInfoMenu.TERMS -> ErrorToast.make(
            binding.root,
            requireContext().getString(item.StringRes),
        ).show()

        MyInfoMenu.PERSONAL_INFO_POLICY -> ErrorToast.make(
            binding.root,
            requireContext().getString(item.StringRes),
        ).show()

        MyInfoMenu.COMMUNITY_RULE -> ErrorToast.make(
            binding.root,
            requireContext().getString(item.StringRes),
        ).show()

        MyInfoMenu.LOGOUT -> ErrorToast.make(
            binding.root,
            requireContext().getString(item.StringRes),
        ).show()

        MyInfoMenu.QUIT -> ErrorToast.make(binding.root, requireContext().getString(item.StringRes))
            .show()
    }

    override fun render(current: MyInfoViewState) {
        super.render(current)

        binding.apply {
            imageViewProfile.loadProfileUrl(
                current.profileUrl,
                com.onewx2m.design_system.R.drawable.ic_profile,
                PROFILE_RADIUS,
            )
            textViewNickname.text = current.nickname
        }
    }

    override fun handleSideEffect(sideEffect: MyInfoSideEffect) {
        super.handleSideEffect(sideEffect)

        when (sideEffect) {
            MyInfoSideEffect.GoToEdit -> {
                val action = MyInfoFragmentDirections.actionMyInfoToEditMyInfo()
                findNavController().navigate(action)
            }
        }
    }
}
