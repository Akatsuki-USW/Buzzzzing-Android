package com.onewx2m.mvi

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.viewbinding.ViewBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

abstract class MviBottomSheetFragment<B : ViewBinding, STATE : ViewState, EVENT : Event, SIDE_EFFECT : SideEffect, VM : MviViewModel<STATE, EVENT, SIDE_EFFECT>>(
    private val inflater: (LayoutInflater, ViewGroup?, Boolean) -> B,
) : Fragment() {
    private var _binding: B? = null
    protected val binding
        get() = _binding!!

    protected abstract val viewModel: VM

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = inflater(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // ViewBinding을 사용했기 때문에 binding.lifecycleOwner = viewLifecycleOwner 하지 않음.
        initView()
        observeState(::render, ::handleSideEffect)
    }

    protected abstract fun initView()

    private fun observeState(render: (STATE) -> Unit, handleSideEffect: (SIDE_EFFECT) -> Unit) {
        repeatOnStarted(viewLifecycleOwner) {
            viewModel.state.onEach {
                render(it)
            }.launchIn(this)

            viewModel.sideEffects.onEach {
                handleSideEffect(it)
            }.launchIn(this)
        }
    }

    protected open fun render(current: STATE) {
    }

    protected open fun handleSideEffect(sideEffect: SIDE_EFFECT) {
    }

    protected fun LifecycleOwner.repeatOnStarted(viewLifecycleOwner: LifecycleOwner, block: suspend CoroutineScope.() -> Unit) {
        viewLifecycleOwner.lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED, block)
        }
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}
