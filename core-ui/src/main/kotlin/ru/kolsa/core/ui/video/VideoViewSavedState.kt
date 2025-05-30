package ru.kolsa.core.ui.video

import android.os.Parcelable
import android.view.View.BaseSavedState
import kotlinx.android.parcel.Parcelize

@Parcelize
class VideoViewSavedState(
    val superSavedState: Parcelable?,
    val isPlaying: Boolean = false,
    val isFirstPlay: Boolean = true,
    val currentWindow: Int = 0,
    val playbackPosition: Long = 0,
    val isFullscreen: Boolean = false,
) : BaseSavedState(superSavedState), Parcelable
