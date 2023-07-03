package com.onewx2m.login_signup.ui.signup.profileandnickname

import android.graphics.Rect
import android.view.ViewTreeObserver
import android.view.ViewTreeObserver.OnGlobalLayoutListener
import androidx.fragment.app.viewModels
import com.onewx2m.core_ui.extensions.px
import com.onewx2m.core_ui.extensions.textChangesToFlow
import com.onewx2m.core_ui.util.Constants
import com.onewx2m.feature_login_signup.databinding.FragmentProfileAndNicknameBinding
import com.onewx2m.mvi.MviFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import timber.log.Timber
import kotlin.properties.Delegates

@AndroidEntryPoint
class ProfileAndNicknameFragment :
    MviFragment<FragmentProfileAndNicknameBinding, ProfileAndNicknameViewState, ProfileAndNicknameEvent, ProfileAndNicknameSideEffect, ProfileAndNicknameViewModel>(
        FragmentProfileAndNicknameBinding::inflate,
    ) {
    override val viewModel: ProfileAndNicknameViewModel by viewModels()

    private val visibleFrameSize = Rect()
    private var rootHeight by Delegates.notNull<Int>()
    private val parentBottomToSignUpButtonTopHeight = 150.px
    private val minKeyboardHeight = 150.px
    private lateinit var viewTreeObserver: ViewTreeObserver
    private lateinit var globalLayoutListener: OnGlobalLayoutListener

    override fun onStart() {
        super.onStart()
        // TODO onStart에서 실행하지 않으면 크래시 발생
        initKeyboardObserve()
    }

    @OptIn(ExperimentalCoroutinesApi::class, FlowPreview::class)
    override fun initView() {
        repeatOnStarted(viewLifecycleOwner) {
            binding.textInputLayoutNickname.editText.textChangesToFlow()
                .filter { nickname ->
                    viewModel.checkNicknameRegexAndUpdateUi(nickname)
                }.debounce(Constants.NICKNAME_INPUT_DEBOUNCE)
                .onEach {
                    Timber.d("$it")
                }
                .launchIn(this)
        }
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
                viewModel.doWhenKeyboardShow(
                    binding.scrollView.scrollY,
                    parentBottomToSignUpButtonTopHeight,
                )
            }
        }
    }

    private fun addOnGlobalListener() {
        viewTreeObserver = binding.root.viewTreeObserver
        viewTreeObserver.addOnGlobalLayoutListener(globalLayoutListener)
    }

    override fun render(current: ProfileAndNicknameViewState) {
        super.render(current)

        binding.textInputLayoutNickname.apply {
            state = current.nicknameLayoutState
            helperText = getString(current.nicknameLayoutHelperTextResId)
        }
    }

    override fun handleSideEffect(sideEffect: ProfileAndNicknameSideEffect) {
        super.handleSideEffect(sideEffect)

        when (sideEffect) {
            is ProfileAndNicknameSideEffect.MoreScroll -> {
                binding.scrollView.run {
                    smoothScrollTo(scrollX, scrollY + sideEffect.scrollY)
                }
            }
        }
    }

    override fun onStop() {
        viewTreeObserver.removeOnGlobalLayoutListener(globalLayoutListener)
        super.onStop()
    }
}
