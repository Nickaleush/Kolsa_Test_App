package ru.kolsa.core.ui.video

import android.os.Parcelable

class VideoViewMutableState : VideoViewState {

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
    fun toSavedState(
        superState: Parcelable?
    ) = VideoViewSavedState(
        superState,
        isPlaying,
        isFirstPlay,
        currentWindow,
        playbackPosition,
        isFullscreen
    )

    fun applySavedState(
        savedState: VideoViewSavedState
    ) {
        isPlaying = savedState.isPlaying
        isFirstPlay = savedState.isFirstPlay
        currentWindow = savedState.currentWindow
        playbackPosition = savedState.playbackPosition
        isFullscreen = savedState.isFullscreen
    }
}
