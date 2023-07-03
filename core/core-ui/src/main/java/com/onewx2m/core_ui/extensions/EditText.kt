package com.onewx2m.core_ui.extensions

import android.widget.EditText
import androidx.core.widget.doOnTextChanged
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.onStart

@ExperimentalCoroutinesApi
fun EditText.textChangesToFlow(): Flow<CharSequence?> {
    return callbackFlow {
        val listener = doOnTextChanged { text, _, _, _ ->
            this@textChangesToFlow.requestFocus() // Focus가 사라지는 현상 방지
            trySend(text)
        }
        awaitClose { removeTextChangedListener(listener) }
    }.onStart { emit(text) }
}
