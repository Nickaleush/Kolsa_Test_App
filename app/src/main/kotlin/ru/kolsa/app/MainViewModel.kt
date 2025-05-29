package ru.kolsa.app

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import ru.kolsa.app.MainContract.Intent
import ru.kolsa.app.MainContract.State

class MainViewModel(
) : ViewModel() {

    private val stateFlow = MutableStateFlow<State>(State.None)

    val state: StateFlow<State>
        get() = stateFlow.asStateFlow()

    fun intent(intent: Intent) {
        when (intent) {
            Intent.SplashScreenEnded -> {
                viewModelScope.launch {
                    stateFlow.value = State.Main
                }
            }
        }
    }
}