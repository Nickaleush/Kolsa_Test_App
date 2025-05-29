package ru.kolsa.core.ui.extensions

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

@Suppress("FunctionName")
public inline fun <reified T : ViewModel> SingleViewModelFactory(
    crossinline block: () -> T
): ViewModelProvider.Factory {
    return object : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST", "DontForceCast")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return block() as T
        }
    }
}