package ru.kolsa.core.ui.extensions

import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow

inline fun <reified T> Fragment.collectFlowWithLifecycle(
    flow: Flow<T>,
    state: Lifecycle.State = Lifecycle.State.STARTED,
    crossinline block: suspend (T) -> Unit
): Job = viewLifecycleOwner.collectFlow(flow, state, block)

fun Fragment.showToast(text: String, length: Int = Toast.LENGTH_SHORT) {
    requireContext().showToast(text, length)
}

fun Fragment.hideKeyboard() {
    requireActivity().inputMethodManager.hideSoftInputFromWindow(view?.windowToken, 0)
}