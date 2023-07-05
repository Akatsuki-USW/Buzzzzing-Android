package com.onewx2m.login_signup.ui.signup.profileandnickname

import android.graphics.Rect
import android.view.ViewTreeObserver
import android.view.ViewTreeObserver.OnGlobalLayoutListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import coil.load
import coil.transform.RoundedCornersTransformation
import com.onewx2m.core_ui.extensions.loadProfileUri
import com.onewx2m.core_ui.extensions.onThrottleClick
import com.onewx2m.core_ui.extensions.px
import com.onewx2m.core_ui.extensions.textChangesToFlow
import com.onewx2m.core_ui.util.Constants
import com.onewx2m.core_ui.util.PermissionManager
import com.onewx2m.design_system.components.toast.ErrorToast
import com.onewx2m.feature_login_signup.R
import com.onewx2m.feature_login_signup.databinding.FragmentProfileAndNicknameBinding
import com.onewx2m.login_signup.ui.signup.SignUpViewModel
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
class ProfileAndNicknameFragment :
    MviFragment<FragmentProfileAndNicknameBinding, ProfileAndNicknameViewState, ProfileAndNicknameEvent, ProfileAndNicknameSideEffect, ProfileAndNicknameViewModel>(
        FragmentProfileAndNicknameBinding::inflate,
    ) {
    override val viewModel: ProfileAndNicknameViewModel by viewModels()
    private val parentViewModel: SignUpViewModel by viewModels({ requireParentFragment() })

    private val visibleFrameSize = Rect()
    private var rootHeight by Delegates.notNull<Int>()
    private val scrollFroNicknameCorrectionValue = 180.px
    private val minKeyboardHeight = 150.px
    private lateinit var viewTreeObserver: ViewTreeObserver
    private lateinit var globalLayoutListener: OnGlobalLayoutListener

    @OptIn(ExperimentalCoroutinesApi::class, FlowPreview::class)
    override fun initView() {
        /**
         *
         * ViewPager2에서 사용자가 다른 화면에 진입했다가 다시 돌아오는 경우 닉네임 중복 검사를 다시 실행하기 위해 repeatOnStarted를 사용하지 않음
         */
        viewLifecycleOwner.lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.RESUMED) {
                binding.textInputLayoutNickname.editText.textChangesToFlow()
                    .map { nickname ->
                        viewModel.postNicknameStateNormalOrInactiveEvent(binding.textInputLayoutNickname.editText.isFocused)
                        viewModel.postSignUpButtonStateDisableSideEffect()
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
            }
        }

        binding.imageViewEditProfile.onThrottleClick {
            viewModel.postGetPermissionAndShowImagePickerSideEffect()
        }
    }

    override fun render(current: ProfileAndNicknameViewState) {
        super.render(current)

        binding.textInputLayoutNickname.apply {
            state = current.nicknameLayoutState
            helperText = getString(current.nicknameLayoutHelperTextResId)
        }

        binding.imageViewProfile.loadProfileUri(current.profileUri, com.onewx2m.design_system.R.drawable.ic_profile)
    }

    override fun handleSideEffect(sideEffect: ProfileAndNicknameSideEffect) {
        super.handleSideEffect(sideEffect)

        when (sideEffect) {
            is ProfileAndNicknameSideEffect.MoreScroll -> {
                binding.scrollView.run {
                    smoothScrollTo(scrollX, scrollY + sideEffect.scrollY)
                }
            }

            is ProfileAndNicknameSideEffect.ShowErrorToast -> ErrorToast.make(
                binding.root,
                sideEffect.message,
            ).show()

            is ProfileAndNicknameSideEffect.ChangeSignUpButtonState -> parentViewModel.postChangeMainButtonStateEvent(
                sideEffect.buttonState,
            )

            is ProfileAndNicknameSideEffect.UpdateSignUpNickname ->
                parentViewModel.availableNickname =
                    sideEffect.nickname

            is ProfileAndNicknameSideEffect.UpdateSignUpProfileUri ->
                parentViewModel.profileUri =
                    sideEffect.profileUri

            ProfileAndNicknameSideEffect.GetPermissionAndShowImagePicker -> {
                PermissionManager.createGetImageAndCameraPermission {
                    TedImagePicker.with(requireContext())
                        .start { uri ->
                            viewModel.updateProfileUri(uri)
                            viewModel.updateSignUpProfileUri(uri)
                        }
                }
            }
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
        globalLayoutListener = OnGlobalLayoutListener {
            binding.root.getWindowVisibleDisplayFrame(visibleFrameSize)
            val heightExceptKeyboard = visibleFrameSize.bottom - visibleFrameSize.top

            // 키보드를 제외한 높이가 디바이스 root_view보다 높거나 같다면, 키보드가 올라왔을 때가 아니므로 거른다.
            if (heightExceptKeyboard < rootHeight - minKeyboardHeight) {
                val keyboardHeight = rootHeight - heightExceptKeyboard
                viewModel.doWhenKeyboardShow(
                    binding.scrollView.scrollY,
                    keyboardHeight - scrollFroNicknameCorrectionValue,
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
