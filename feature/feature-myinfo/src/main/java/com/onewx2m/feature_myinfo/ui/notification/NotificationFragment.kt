package com.onewx2m.feature_myinfo.ui.notification

import android.os.Bundle
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.onewx2m.core_ui.extensions.onThrottleClick
import com.onewx2m.core_ui.util.DeepLinkUtil
import com.onewx2m.design_system.components.recyclerview.notification.NotificationAdapter
import com.onewx2m.design_system.components.toast.ErrorToast
import com.onewx2m.feature_myinfo.databinding.FragmentNotificationBinding
import com.onewx2m.mvi.MviFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NotificationFragment :
    MviFragment<FragmentNotificationBinding, NotificationViewState, NotificationEvent, NotificationSideEffect, NotificationViewModel>(
        FragmentNotificationBinding::inflate,
    ) {
    override val viewModel: NotificationViewModel by viewModels()

    private val listAdapter: NotificationAdapter by lazy {
        NotificationAdapter {
            viewModel.readNotification(it.notificationId, it.redirectTargetId)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel.getNotificationList()
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

    override fun render(current: NotificationViewState) {
        super.render(current)

        listAdapter.submitList(current.notificationList)
    }

    override fun handleSideEffect(sideEffect: NotificationSideEffect) {
        super.handleSideEffect(sideEffect)

        when (sideEffect) {
            NotificationSideEffect.GoToLoginFragment -> goToLoginFragment()
            is NotificationSideEffect.GoToSpotDetailFragment -> goToSpotDetailFragment(sideEffect.spotId)
            NotificationSideEffect.PopBackStack -> findNavController().popBackStack()
            is NotificationSideEffect.ShowErrorToast -> ErrorToast.make(
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

    private fun goToSpotDetailFragment(spotId: Int) {
        val (request, navOptions) = DeepLinkUtil.getSpotDetailRequestAndOption(
            requireContext(),
            spotId,
        )
        findNavController().navigate(request, navOptions)
    }
}
