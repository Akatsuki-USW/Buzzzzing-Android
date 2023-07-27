package com.onewx2m.recommend_place.ui.write

import android.graphics.Rect
import android.os.Bundle
import android.view.ViewTreeObserver
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.onewx2m.core_ui.extensions.onThrottleClick
import com.onewx2m.core_ui.extensions.px
import com.onewx2m.core_ui.util.PermissionManager
import com.onewx2m.design_system.components.recyclerview.picture.PictureAdapter
import com.onewx2m.design_system.components.recyclerview.spotcategoryselector.SpotCategorySelectorAdapter
import com.onewx2m.design_system.enum.Congestion
import com.onewx2m.mvi.MviFragment
import com.onewx2m.recommend_place.R
import com.onewx2m.recommend_place.databinding.FragmentWriteBinding
import com.onewx2m.recommend_place.ui.write.bottomsheet.BuzzzzingLocationBottomSheet
import com.onewx2m.recommend_place.ui.write.bottomsheet.KakaoLocationBottomSheet
import dagger.hilt.android.AndroidEntryPoint
import gun0912.tedimagepicker.builder.TedImagePicker
import kotlin.properties.Delegates

@AndroidEntryPoint
class WriteFragment :
    MviFragment<FragmentWriteBinding, WriteViewState, WriteEvent, WriteSideEffect, WriteViewModel>(
        FragmentWriteBinding::inflate,
    ) {
    companion object {
        private const val SCROLL_FOR_CONTENT_Y_AXIS = 0
        private const val MIN_KEY_BOARD_HEIGHT = 150

        private const val MAX_PICTURE = 5
    }

    private val visibleFrameSize = Rect()
    private var rootHeight by Delegates.notNull<Int>()
    private lateinit var viewTreeObserver: ViewTreeObserver
    private lateinit var globalLayoutListener: ViewTreeObserver.OnGlobalLayoutListener

    override val viewModel: WriteViewModel by viewModels()

    private var spotCategorySelectorAdapter: SpotCategorySelectorAdapter? = null
    private val pictureAdapter: PictureAdapter by lazy {
        PictureAdapter {
            viewModel.removePicture(it)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel.initSpotCategoryItems()
    }

    override fun initView() {
        binding.apply {
            recyclerViewCategory.apply {
                layoutManager = LinearLayoutManager(requireContext()).apply {
                    orientation = LinearLayoutManager.HORIZONTAL
                }
            }

            recyclerViewImage.apply {
                layoutManager = LinearLayoutManager(requireContext()).apply {
                    orientation = LinearLayoutManager.HORIZONTAL
                }
                adapter = pictureAdapter
            }

            imageViewAddImage.onThrottleClick {
                viewModel.showImagePicker()
            }

            textInputLayoutAddress.editText.apply {
                isFocusable = false
                onThrottleClick {
                    viewModel.postShowKakaoLocationBottomSheetSideEffect()
                }
            }
            textInputLayoutBuzzzzingLocation.editText.apply {
                isFocusable = false
                onThrottleClick {
                    viewModel.postShowBuzzzzingLocationBottomSheetSideEffect()
                }
            }
            textInputLayoutTitle.editText.doOnTextChanged { text, _, _, _ ->
                viewModel.updateTitle(text.toString())
            }
            textInputLayoutContent.editText.doOnTextChanged { text, _, _, _ ->
                viewModel.updateContent(text.toString())
            }
            buttonMain.onThrottleClick {
                viewModel.postSpot()
            }
        }
    }

    override fun render(current: WriteViewState) {
        super.render(current)

        if (spotCategorySelectorAdapter == null && current.spotCategoryItems.isNotEmpty()) {
            spotCategorySelectorAdapter = SpotCategorySelectorAdapter(
                Congestion.NORMAL,
                current.spotCategoryItems,
                current.selectedSpotCategoryItem,
            ) {
                viewModel.updateSelectedCategoryItem(it)
            }
        }

        if (spotCategorySelectorAdapter != null && binding.recyclerViewCategory.adapter == null) {
            binding.recyclerViewCategory.adapter =
                spotCategorySelectorAdapter
        }

        binding.textInputLayoutAddress.apply {
            editText.setText(current.kakaoLocation)
        }

        binding.textInputLayoutBuzzzzingLocation.apply {
            editText.setText(current.buzzzzingLocation)
        }

        if (current.needTitleRender) {
            binding.textInputLayoutTitle.editText.setText(current.title)
            binding.textInputLayoutTitle.editText.setSelection(current.title.length)
        }

        if (current.needContentRender) {
            binding.textInputLayoutContent.editText.setText(current.content)
            binding.textInputLayoutContent.editText.setSelection(current.content.length)
        }

        binding.buttonMain.state = current.mainButtonState

        pictureAdapter.submitList(current.pictures)
    }

    override fun handleSideEffect(sideEffect: WriteSideEffect) {
        super.handleSideEffect(sideEffect)

        when (sideEffect) {
            is WriteSideEffect.MoreScroll -> {
                binding.scrollView.run {
                    smoothScrollTo(scrollX, scrollY + sideEffect.scrollY)
                }
            }

            WriteSideEffect.ShowBuzzzzingLocationBottomSheet -> showBuzzzzingLocationBottomSheet()
            WriteSideEffect.ShowKakaoLocationBottomSheet -> showKakaoLocationBottomSheet()
            WriteSideEffect.GetPermissionAndShowImagePicker -> PermissionManager.createGetImageAndCameraPermission {
                TedImagePicker.with(requireContext())
                    .max(
                        MAX_PICTURE - viewModel.state.value.pictureUrls.size,
                        R.string.max_notification,
                    )
                    .selectedUri(viewModel.state.value.pictureUris)
                    .startMultiImage { uris ->
                        viewModel.updatePictures(uris)
                    }
            }
        }
    }

    private fun showKakaoLocationBottomSheet() {
        KakaoLocationBottomSheet.newInstance {
            viewModel.onClickKakaoLocationItem(it)
        }.show(parentFragmentManager, "")
    }

    private fun showBuzzzzingLocationBottomSheet() {
        BuzzzzingLocationBottomSheet.newInstance {
            viewModel.onClickBuzzzzingLocationItem(it)
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
