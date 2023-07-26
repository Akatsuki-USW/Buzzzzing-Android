package com.onewx2m.recommend_place.ui.write

import android.graphics.Rect
import android.view.ViewTreeObserver
import androidx.fragment.app.viewModels
import com.onewx2m.core_ui.extensions.onThrottleClick
import com.onewx2m.core_ui.extensions.px
import com.onewx2m.mvi.MviFragment
import com.onewx2m.recommend_place.databinding.FragmentWriteBinding
import com.onewx2m.recommend_place.ui.write.bottomsheet.KakaoLocationBottomSheet
import dagger.hilt.android.AndroidEntryPoint
import kotlin.properties.Delegates

@AndroidEntryPoint
class WriteFragment :
    MviFragment<FragmentWriteBinding, WriteViewState, WriteEvent, WriteSideEffect, WriteViewModel>(
        FragmentWriteBinding::inflate,
    ) {
    companion object {
        private const val SCROLL_FOR_CONTENT_Y_AXIS = 0
        private const val MIN_KEY_BOARD_HEIGHT = 150
    }

    private val visibleFrameSize = Rect()
    private var rootHeight by Delegates.notNull<Int>()
    private lateinit var viewTreeObserver: ViewTreeObserver
    private lateinit var globalLayoutListener: ViewTreeObserver.OnGlobalLayoutListener

    override val viewModel: WriteViewModel by viewModels()

    override fun initView() {
        binding.apply {
            textInputLayoutAddress.editText.apply {
                isFocusable = false
                onThrottleClick {
                    showKakaoLocationBottomSheet()
                }
            }
            textInputLayoutBuzzzzingLocation.editText.isFocusable = false
        }
    }

    override fun handleSideEffect(sideEffect: WriteSideEffect) {
        super.handleSideEffect(sideEffect)

        when (sideEffect) {
            is WriteSideEffect.MoreScroll -> {
                binding.scrollView.run {
                    smoothScrollTo(scrollX, scrollY + sideEffect.scrollY)
                }
            }
        }
    }

    private fun showKakaoLocationBottomSheet() {
        KakaoLocationBottomSheet.newInstance {
        }.show(parentFragmentManager, "")
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
            if (heightExceptKeyboard < rootHeight - MIN_KEY_BOARD_HEIGHT.px && binding.textInputLayoutContent.editText.isFocused) {
                val keyboardHeight = rootHeight - heightExceptKeyboard
                viewModel.doWhenKeyboardShow(
                    binding.scrollView.scrollY,
                    keyboardHeight - SCROLL_FOR_CONTENT_Y_AXIS.px,
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
