package com.onewx2m.feature_myinfo.ui.editmyinfo

import android.graphics.Rect
import android.view.View
import android.view.ViewTreeObserver
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.onewx2m.core_ui.extensions.loadProfileUri
import com.onewx2m.core_ui.extensions.loadProfileUrl
import com.onewx2m.core_ui.extensions.onThrottleClick
import com.onewx2m.core_ui.extensions.px
import com.onewx2m.core_ui.extensions.textChangesToFlow
import com.onewx2m.core_ui.util.BuzzzzingUser
import com.onewx2m.core_ui.util.Constants
import com.onewx2m.core_ui.util.PermissionManager
import com.onewx2m.design_system.components.button.MainButtonState
import com.onewx2m.design_system.components.toast.ErrorToast
import com.onewx2m.feature_myinfo.databinding.FragmentEditMyInfoBinding
import com.onewx2m.mvi.MviFragment
import dagger.hilt.android.AndroidEntryPoint
import gun0912.tedimagepicker.builder.TedImagePicker
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import kotlin.properties.Delegates

@AndroidEntryPoint
class EditMyInfoFragment :
    MviFragment<FragmentEditMyInfoBinding, EditMyInfoViewState, EditMyInfoEvent, EditMyInfoSideEffect, EditMyInfoViewModel>(
        FragmentEditMyInfoBinding::inflate,
    ) {
    override val viewModel: EditMyInfoViewModel by viewModels()

    companion object {
        private const val PROFILE_RADIUS = 20
        private const val SCROLL_FOR_EDITTEXT_Y_AXIS = 220
        private const val MIN_KEY_BOARD_HEIGHT = 150
    }

    private val visibleFrameSize = Rect()
    private var rootHeight by Delegates.notNull<Int>()
    private lateinit var viewTreeObserver: ViewTreeObserver
    private lateinit var globalLayoutListener: ViewTreeObserver.OnGlobalLayoutListener

    override fun initView() {
        binding.apply {
            textInputLayoutNickname.editText.setText(BuzzzzingUser.nickname)
            textInputLayoutEmail.editText.setText(BuzzzzingUser.email)
            imageViewEditProfile.onThrottleClick {
                viewModel.postGetPermissionAndShowImagePickerSideEffect()
            }
            imageViewBack.onThrottleClick {
                viewModel.goToPrev()
            }
            buttonMain.onThrottleClick {
                viewModel.editMyInfo()
            }
        }

        observeTextInputLayout()
    }

    @OptIn(ExperimentalCoroutinesApi::class, FlowPreview::class)
    private fun observeTextInputLayout() {
        /**
         *
         * ViewPager2에서 사용자가 다른 화면에 진입했다가 다시 돌아오는 경우 닉네임 중복 검사를 다시 실행하기 위해 repeatOnStarted를 사용하지 않음
         */
        viewLifecycleOwner.lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.RESUMED) {
                binding.textInputLayoutNickname.editText.textChangesToFlow()
                    .map { nickname ->
                        viewModel.postNicknameStateNormalOrInactiveEvent(binding.textInputLayoutNickname.editText.isFocused)
                        viewModel.postChangeMainButtonStateEvent(MainButtonState.DISABLE)
                        nickname
                    }
                    .debounce(Constants.NICKNAME_INPUT_DEBOUNCE)
                    .filter { nickname ->
                        viewModel.checkNicknameRegexAndUpdateUi(
                            nickname,
                            binding.textInputLayoutNickname.editText.isFocused,
                        )
                    }
                    .onEach { nickname ->
                        viewModel.verifyNicknameFromServer(nickname.toString())
                    }
                    .launchIn(this)

                binding.textInputLayoutEmail.editText.textChangesToFlow()
                    .map { email ->
                        viewModel.postEmailStateNormalOrInactiveEvent(binding.textInputLayoutEmail.editText.isFocused)
                        viewModel.postChangeMainButtonStateEvent(MainButtonState.DISABLE)
                        email
                    }
                    .debounce(Constants.EMAIL_INPUT_DEBOUNCE)
                    .onEach { email ->
                        viewModel.checkEmailRegexAndUpdateUi(
                            email,
                            binding.textInputLayoutEmail.editText.isFocused,
                        )
                    }
                    .launchIn(this)
            }
        }
    }

    override fun render(current: EditMyInfoViewState) {
        super.render(current)

        binding.apply {
            scrollView.visibility =
                if (current.isScrollViewVisible) View.VISIBLE else View.INVISIBLE
            lottieLoading.visibility = if (current.isLottieVisible) View.VISIBLE else View.INVISIBLE

            buttonMain.state = current.mainButtonState

            textInputLayoutNickname.apply {
                state = current.nicknameLayoutState
                helperText = getString(current.nicknameLayoutHelperTextResId)
            }

            textInputLayoutEmail.apply {
                state = current.emailLayoutState
                helperText = getString(current.emailLayoutHelperTextResId)
            }

            current.profileUri?.let {
                imageViewProfile.loadProfileUri(
                    it,
                    com.onewx2m.design_system.R.drawable.ic_profile,
                    PROFILE_RADIUS,
                )
            } ?: imageViewProfile.loadProfileUrl(
                BuzzzzingUser.profileImageUrl,
                com.onewx2m.design_system.R.drawable.ic_profile,
                PROFILE_RADIUS,
            )
        }
    }

    override fun handleSideEffect(sideEffect: EditMyInfoSideEffect) {
        super.handleSideEffect(sideEffect)

        when (sideEffect) {
            is EditMyInfoSideEffect.MoreScroll -> {
                binding.scrollView.run {
                    smoothScrollTo(scrollX, scrollY + sideEffect.scrollY)
                }
            }

            is EditMyInfoSideEffect.ShowErrorToast -> ErrorToast.make(
                binding.root,
                sideEffect.message,
            ).show()

            EditMyInfoSideEffect.GetPermissionAndShowImagePicker -> {
                PermissionManager.createGetImageAndCameraPermission {
                    TedImagePicker.with(requireContext())
                        .start { uri ->
                            viewModel.updateProfileUri(uri)
                        }
                }
            }

            EditMyInfoSideEffect.GoToPrev -> {
                findNavController().popBackStack()
            }

            EditMyInfoSideEffect.PlayLottie -> binding.lottieLoading.playAnimation()
            EditMyInfoSideEffect.StopLottie -> binding.lottieLoading.cancelAnimation()
        }
    }

    override fun onStart() {
        super.onStart()
        // TODO onStart에서 실행하지 않으면 크래시 발생
        initKeyboardObserve()
    }

    private fun initKeyboardObserve() {
        initRootHeight()
        initGlobalLayoutListener()
        addOnGlobalListener()
    }

    private fun initRootHeight() {
        binding.root.getWindowVisibleDisplayFrame(visibleFrameSize)
        rootHeight = visibleFrameSize.bottom - visibleFrameSize.top
    }

    private fun initGlobalLayoutListener() {
        globalLayoutListener = ViewTreeObserver.OnGlobalLayoutListener {
            binding.root.getWindowVisibleDisplayFrame(visibleFrameSize)
            val heightExceptKeyboard = visibleFrameSize.bottom - visibleFrameSize.top

            // 키보드를 제외한 높이가 디바이스 root_view보다 높거나 같다면, 키보드가 올라왔을 때가 아니므로 거른다.
            if (heightExceptKeyboard < rootHeight - MIN_KEY_BOARD_HEIGHT.px) {
                val keyboardHeight = rootHeight - heightExceptKeyboard
                viewModel.doWhenKeyboardShow(
                    binding.scrollView.scrollY,
                    keyboardHeight - SCROLL_FOR_EDITTEXT_Y_AXIS.px,
                )
            }
        }
    }

    private fun addOnGlobalListener() {
        viewTreeObserver = binding.root.viewTreeObserver
        viewTreeObserver.addOnGlobalLayoutListener(globalLayoutListener)
    }

    override fun onStop() {
        viewTreeObserver.removeOnGlobalLayoutListener(globalLayoutListener)
        super.onStop()
    }
}
