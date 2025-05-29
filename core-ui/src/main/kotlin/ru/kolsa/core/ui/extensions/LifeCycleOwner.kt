package ru.kolsa.core.ui.extensions

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

inline fun <reified T> LifecycleOwner.collectFlow(
    flow: Flow<T>,
    state: Lifecycle.State = Lifecycle.State.STARTED,
    crossinline block: suspend (T) -> Unit
): Job = lifecycleScope.launch {
    repeatOnLifecycle(state) {
        flow.collect { value ->
            block(value)
        }
    }
}

data class LoadingException(override val message: String = "") : RuntimeException(message)

val Result<*>.isLoading: Boolean
    get() = exceptionOrNull() is LoadingException

fun <T> Result.Companion.loading(): Result<T> {
    return failure(LoadingException())
}