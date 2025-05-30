package ru.kolsa.feature.workouts.ui.view.videoWithoutFullscreen

import android.annotation.SuppressLint
import android.content.Context
import android.net.Uri
import android.util.AttributeSet
import androidx.cardview.widget.CardView
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.exoplayer.ExoPlayer
import by.kirich1409.viewbindingdelegate.viewBinding
import ru.kolsa.core.ui.databinding.VideoViewBinding
import ru.kolsa.core.ui.extensions.hideView
import ru.kolsa.core.ui.extensions.layoutInflater
import ru.kolsa.core.ui.extensions.showView
import ru.kolsa.feature.workouts.ui.databinding.WorkoutVideoViewBinding
import java.lang.ref.WeakReference

class WorkoutVideoView : CardView {

    private class PlayerListener(
        private val ref: WeakReference<WorkoutVideoView>
    ) : Player.Listener {
        companion object {
            private const val CONTROLLER_SHOW_TIMEOUT_MS = 1000
        }

        @SuppressLint("UnsafeOptInUsageError")
        override fun onPlaybackStateChanged(playbackState: Int) {
            super.onPlaybackStateChanged(playbackState)
            val view = ref.get() ?: return
            when (playbackState) {
                Player.STATE_READY -> {
                    view.binding.videoView.useController = true
                    if (!view.state.isFirstPlay) {
                        view.binding.videoView.controllerShowTimeoutMs = CONTROLLER_SHOW_TIMEOUT_MS
                    }
                    view.state.isFirstPlay = false
                    view.binding.videoProgressBar.hideView(100L)
                    view.binding.videoPlayerPreview.hideView(100L)
                    view.binding.videoView.showController()
                }

                Player.STATE_ENDED -> {
                    view.state.isFirstPlay = true
                    view.binding.videoView.player?.seekTo(0)
                    view.binding.videoView.player?.pause()
                    view.binding.videoPlayerPreview.showView(300L)
                    view.binding.videoView.controllerShowTimeoutMs = 0
                }

                Player.STATE_BUFFERING -> {
                    view.binding.videoProgressBar.showView(300L)
                }

                Player.STATE_IDLE -> {}
            }
        }

        @SuppressLint("UnsafeOptInUsageError")
        override fun onIsPlayingChanged(isPlaying: Boolean) {
            super.onIsPlayingChanged(isPlaying)
            if (isPlaying) {
                ref.get()?.binding?.videoView?.controllerShowTimeoutMs = CONTROLLER_SHOW_TIMEOUT_MS
            }
        }
    }

    private val binding by viewBinding(WorkoutVideoViewBinding::bind)

    private var exoPlayer: ExoPlayer? = null
    private var url: String? = null

    private val state = WorkoutVideoViewMutableState()
    private val playerListener = PlayerListener(WeakReference(this))

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )

    init {
        WorkoutVideoViewBinding.inflate(layoutInflater, this)
    }

    fun configure(url: String?) {
        this.url = url
        initPlayer()
    }

    @SuppressLint("UnsafeOptInUsageError")
    private fun initPlayer() {
        val url = this.url ?: return
        if (exoPlayer != null) return
        binding.videoView.player = ExoPlayer.Builder(context).build().also {
            it.playWhenReady = state.isPlaying
            it.setMediaItem(MediaItem.fromUri(Uri.parse(url)), false)
            it.addListener(playerListener)
            it.prepare()
            exoPlayer = it
        }

        binding.videoPlayerPreview.showView(100L)
        binding.videoView.useController = false

        binding.videoView.setShowMultiWindowTimeBar(true)
        binding.videoView.setShowNextButton(false)
        binding.videoView.setShowPreviousButton(false)
        binding.videoView.setShowRewindButton(false)
        binding.videoView.setShowShuffleButton(false)
        binding.videoView.setShowFastForwardButton(false)
        binding.videoView.controllerShowTimeoutMs = 0
        binding.videoView.controllerAutoShow = false
    }

    fun releasePlayer() {
        val player = exoPlayer ?: return
        player.removeListener(playerListener)
        state.isPlaying = false
        state.playbackPosition = 0
        state.currentWindow = player.currentMediaItemIndex
        player.stop()
        player.release()
        binding.videoView.player = null
        exoPlayer = null
    }
}

