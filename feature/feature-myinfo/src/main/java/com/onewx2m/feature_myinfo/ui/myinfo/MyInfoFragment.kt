package com.onewx2m.feature_myinfo.ui.myinfo

import android.content.Intent
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.gms.oss.licenses.OssLicensesMenuActivity
import com.onewx2m.core_ui.extensions.loadUrl
import com.onewx2m.core_ui.extensions.navigateActionWithDefaultAnim
import com.onewx2m.core_ui.extensions.onThrottleClick
import com.onewx2m.core_ui.util.BuzzzzingUser
import com.onewx2m.core_ui.util.DeepLinkUtil
import com.onewx2m.design_system.components.dialog.CommonDialog
import com.onewx2m.design_system.components.toast.ErrorToast
import com.onewx2m.feature_myinfo.R
import com.onewx2m.feature_myinfo.databinding.FragmentMyInfoBinding
import com.onewx2m.feature_myinfo.ui.myinfo.adapter.MenuAdapter
import com.onewx2m.feature_myinfo.ui.myinfo.adapter.MyInfoMenu
import com.onewx2m.mvi.MviFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
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

    private val commonDialog by lazy {
        CommonDialog(requireContext())
    }

    override fun initView() {
        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = listAdapter
        }
        listAdapter.submitList(MyInfoMenu.values().toList())

        binding.buttonEditMyInfo.onThrottleClick {
            viewModel.goToEdit()
        }

        binding.imageViewAlarm.onThrottleClick {
            viewModel.goToNotification()
        }
    }

    private fun onMenuClick(item: MyInfoMenu) = when (item) {
        MyInfoMenu.MY_ARTICLE -> viewModel.goToMyArticle()

        MyInfoMenu.BAN_HISTORY -> viewModel.goToBanList()

        MyInfoMenu.OPEN_SOURCE -> viewModel.showOpenLicenses()

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

        MyInfoMenu.LOGOUT -> viewModel.showLogoutDialog()

        MyInfoMenu.QUIT -> viewModel.showQuitDialog()
    }

    override fun render(current: MyInfoViewState) {
        super.render(current)

        binding.apply {
            imageViewProfile.loadUrl(
                BuzzzzingUser.profileImageUrl,
                com.onewx2m.design_system.R.drawable.ic_profile,
                PROFILE_RADIUS,
            )
            textViewNickname.text = BuzzzzingUser.nickname
            textViewEmail.text = BuzzzzingUser.email

            lottieLoadingSmall.visibility =
                if (current.isSmallLottieVisible) View.VISIBLE else View.GONE
        }
    }

    override fun handleSideEffect(sideEffect: MyInfoSideEffect) {
        super.handleSideEffect(sideEffect)

        when (sideEffect) {
            MyInfoSideEffect.GoToEdit -> {
                val action = MyInfoFragmentDirections.actionMyInfoToEditMyInfo()
                findNavController().navigateActionWithDefaultAnim(action)
            }

            MyInfoSideEffect.GoToMyArticle -> {
                val action = MyInfoFragmentDirections.actionMyInfoToMyArticle()
                findNavController().navigateActionWithDefaultAnim(action)
            }

            MyInfoSideEffect.GoToNotification -> {
                val action = MyInfoFragmentDirections.actionMyInfoToNotification()
                findNavController().navigateActionWithDefaultAnim(action)
            }

            MyInfoSideEffect.ShowOpenLicenses -> startActivity(
                Intent(
                    requireActivity(),
                    OssLicensesMenuActivity::class.java,
                ),
            )

            MyInfoSideEffect.GoToBanList -> {
                val action = MyInfoFragmentDirections.actionMyInfoToBan()
                findNavController().navigateActionWithDefaultAnim(action)
            }

            MyInfoSideEffect.Logout -> showLogoutCommonDialog()
            MyInfoSideEffect.Quit -> showRevokeCommonDialog()
            is MyInfoSideEffect.ShowErrorToast -> ErrorToast.make(
                binding.root,
                sideEffect.msg,
            ).show()

            MyInfoSideEffect.GoToLoginFragment -> goToLoginFragment()
        }
    }

    private fun showLogoutCommonDialog() {
        commonDialog.setTitle(R.string.my_info_menu_logout)
            .setDescription(R.string.dialog_logout_description)
            .setNegativeButton {
                commonDialog.dismiss()
            }
            .setPositiveButton {
                viewModel.logout()
                commonDialog.dismiss()
            }
            .show()
    }

    private fun showRevokeCommonDialog() {
        commonDialog.setTitle(R.string.my_info_menu_quit)
            .setDescription(R.string.dialog_revoke_description)
            .setNegativeButton {
                commonDialog.dismiss()
            }
            .setPositiveButton {
                viewModel.revoke()
                commonDialog.dismiss()
            }
            .show()
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
