package com.onewx2m.mvi

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

abstract class MviActivity<B : ViewDataBinding, STATE : ViewState, EVENT : Event, SIDE_EFFECT: SideEffect ,VM : MviViewModel<STATE, EVENT, SIDE_EFFECT>>(
    private val inflater: (LayoutInflater) -> B,
) : AppCompatActivity() {
    protected lateinit var binding: B
    protected abstract val viewModel: VM

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = inflater(layoutInflater)
        setContentView(binding.root)
        binding.lifecycleOwner = this

        initView()
        observeState(::render, ::handleSideEffect)
    }

    protected abstract fun initView()

    private fun observeState(render: (STATE) -> Unit, handleSideEffect: (SIDE_EFFECT) -> Unit) {
        repeatOnStarted {
            viewModel.state.onEach {
                render(it)
            }.launchIn(this)

            viewModel.sideEffects.onEach {
                handleSideEffect(it)
            }.launchIn(this)
        }
    }

    protected open fun render(state: STATE) {

    }

    protected open fun handleSideEffect(sideEffect: SIDE_EFFECT) {

    }

    protected fun LifecycleOwner.repeatOnStarted(block: suspend CoroutineScope.() -> Unit) {
        lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED, block)
        }
    }

}