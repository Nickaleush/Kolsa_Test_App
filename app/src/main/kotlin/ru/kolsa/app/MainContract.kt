package ru.kolsa.app

object MainContract {
    sealed class State {
        data object None : State()
        data object Splash : State()

        data object Main : State()
    }

    sealed class Intent {
        data object SplashScreenEnded : Intent()
    }
}