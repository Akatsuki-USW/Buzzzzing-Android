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

@AndroidEntryPoint
class ProfileAndNicknameFragment :
    MviFragment<FragmentProfileAndNicknameBinding, ProfileAndNicknameViewState, ProfileAndNicknameEvent, ProfileAndNicknameSideEffect, ProfileAndNicknameViewModel>(
        FragmentProfileAndNicknameBinding::inflate,
    ) {
    override val viewModel: ProfileAndNicknameViewModel by viewModels()

    private var rootHeight = -1

    private lateinit var viewTreeObserver: ViewTreeObserver
    private var globalLayoutListenerAddedOn: OnGlobalLayoutListener? = null
//    override fun onDestroyView() {
//        Timber.tag("생명주기").d("onDestroyView globalLayoutListenerAddedOn = $globalLayoutListenerAddedOn")
//        Timber.tag("생명주기").d("onDestroyView viewTreeObserver = ${binding.root.viewTreeObserver}")
//        viewTreeObserver.removeOnGlobalLayoutListener(globalLayoutListenerAddedOn)
//        globalLayoutListenerAddedOn = null
//        super.onDestroyView()
//    }

    override fun onStop() {
        Timber.tag("생명주기").d("onDestroyView globalLayoutListenerAddedOn = $globalLayoutListenerAddedOn")
        Timber.tag("생명주기").d("onDestroyView viewTreeObserver = ${binding.root.viewTreeObserver}")
        viewTreeObserver.removeOnGlobalLayoutListener(globalLayoutListenerAddedOn)
        globalLayoutListenerAddedOn = null
        super.onStop()
    }

    override fun onStart() {
        super.onStart()
        Timber.tag("생명주기").d("initView viewTreeObserver = ${binding.root.viewTreeObserver}")

        val visibleFrameSize = Rect()
        binding.root.getWindowVisibleDisplayFrame(visibleFrameSize)
        if (rootHeight == -1) {
            rootHeight =
                visibleFrameSize.bottom - visibleFrameSize.top // 매번 호출되기 때문에, 처음 한 번만 값을 할당해준다.
        }


        viewTreeObserver = binding.root.viewTreeObserver
        viewTreeObserver.addOnGlobalLayoutListener(object : OnGlobalLayoutListener {
            override fun onGlobalLayout() {
                Timber.tag("생명주기").d("onGlobalLayout binding = $binding")
                Timber.tag("생명주기").d("onGlobalLayout viewTreeObserver = ${binding.root.viewTreeObserver}")
                binding.root.getWindowVisibleDisplayFrame(visibleFrameSize)
                if (rootHeight == -1) {
                    rootHeight =
                        visibleFrameSize.bottom - visibleFrameSize.top // 매번 호출되기 때문에, 처음 한 번만 값을 할당해준다.
                }
                val heightExceptKeyboard = visibleFrameSize.bottom - visibleFrameSize.top
                Timber.d("예상 키보드 높이 $heightExceptKeyboard 루트 높이 $rootHeight")
                // 키보드를 제외한 높이가 디바이스 root_view보다 높거나 같다면, 키보드가 올라왔을 때가 아니므로 거른다.
                if (heightExceptKeyboard < rootHeight) {
                    // 키보드 높이
                    val keyboardHeight = visibleFrameSize.bottom - 100.px
                    Timber.d("키보드 보임 감지 / 보이는 곳 바텀 : $keyboardHeight / 인풋 박스 바텀 : ${binding.textInputLayoutNickname.bottom}")
                    binding.scrollView.run {
                        smoothScrollTo(
                            scrollX,
                            (binding.textInputLayoutNickname.bottom - keyboardHeight).px,
                        )
                    }
                }
                globalLayoutListenerAddedOn = this
                Timber.tag("생명주기").d("onGlobalLayout globalLayoutListenerAddedOn = $globalLayoutListenerAddedOn")
//                binding.root.viewTreeObserver.removeOnGlobalLayoutListener(this)
            }
        })
    }

    @OptIn(ExperimentalCoroutinesApi::class, FlowPreview::class)
    override fun initView() {


//        listener = object : OnGlobalLayoutListener {
//            override fun onGlobalLayout() {
//                binding.root.getWindowVisibleDisplayFrame(visibleFrameSize)
//                if (rootHeight == -1) {
//                    rootHeight =
//                        visibleFrameSize.bottom - visibleFrameSize.top // 매번 호출되기 때문에, 처음 한 번만 값을 할당해준다.
//                }
//                val heightExceptKeyboard = visibleFrameSize.bottom - visibleFrameSize.top
//                Timber.d("예상 키보드 높이 $heightExceptKeyboard 루트 높이 $rootHeight")
//                // 키보드를 제외한 높이가 디바이스 root_view보다 높거나 같다면, 키보드가 올라왔을 때가 아니므로 거른다.
//                if (heightExceptKeyboard < rootHeight) {
//                    // 키보드 높이
//                    val keyboardHeight = visibleFrameSize.bottom - 100.px
//                    Timber.d("키보드 보임 감지 / 보이는 곳 바텀 : $keyboardHeight / 인풋 박스 바텀 : ${binding.textInputLayoutNickname.bottom}")
//                    binding.scrollView.run {
//                        smoothScrollTo(
//                            scrollX,
//                            (binding.textInputLayoutNickname.bottom - keyboardHeight).px,
//                        )
//                    }
//                }
//                binding.root.viewTreeObserver.removeOnGlobalLayoutListener(this)
//            }
//        }



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
            ProfileAndNicknameSideEffect.ScrollToKeyBoardHeight -> {
                binding.scrollView.apply {
                    smoothScrollTo(scrollX, Constants.NORMAL_KEYBOARD_HEIGHT)
                }
            }
        }
    }
}
