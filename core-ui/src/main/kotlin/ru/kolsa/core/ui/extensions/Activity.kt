package ru.kolsa.core.ui.extensions

import androidx.activity.ComponentActivity
import androidx.lifecycle.Lifecycle
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow

inline fun <reified T> ComponentActivity.collectFlowWithLifecycle(
    flow: Flow<T>,
    state: Lifecycle.State = Lifecycle.State.STARTED,
    crossinline block: suspend (T) -> Unit
): Job = collectFlow(flow, state, block)
