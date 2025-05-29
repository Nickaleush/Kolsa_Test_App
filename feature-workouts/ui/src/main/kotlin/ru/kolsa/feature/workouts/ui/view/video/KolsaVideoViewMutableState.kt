package ru.kolsa.feature.workouts.ui.view.video

internal class KolsaVideoViewMutableState : KolsaVideoViewState {

    var isPlaying: Boolean = false
    var isFirstPlay: Boolean = true
    var currentWindow: Int = 0
    var playbackPosition: Long = 0
    override var isFullscreen: Boolean = false
        set(newValue) {
            val oldValue = field
            field = newValue
            if (oldValue != newValue) {
                onFullscreenChangedListener()
            }
        }

    var onFullscreenChangedListener: () -> Unit = {}
}
