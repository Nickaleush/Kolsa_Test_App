package ru.kolsa.core.ui.video

import android.annotation.SuppressLint
import android.content.Context
import android.net.Uri
import android.os.Parcelable
import android.util.AttributeSet
import android.view.ViewGroup
import android.view.ViewParent
import android.widget.FrameLayout
import androidx.cardview.widget.CardView
import androidx.core.view.isVisible
import androidx.core.widget.NestedScrollView
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.findViewTreeLifecycleOwner
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.exoplayer.ExoPlayer
import by.kirich1409.viewbindingdelegate.viewBinding
import ru.kolsa.core.ui.databinding.VideoViewBinding
import ru.kolsa.core.ui.extensions.dp
import ru.kolsa.core.ui.extensions.hideView
import ru.kolsa.core.ui.extensions.layoutInflater
import ru.kolsa.core.ui.extensions.showView
import java.lang.ref.WeakReference

class VideoView : CardView {

    interface Delegate {
        fun onFullScreenClick(state: VideoViewState)
    }

    private class PlayerListener(
        private val ref: WeakReference<VideoView>
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
                    view.binding.videoProgressBar.showView(100L)
                }

                Player.STATE_IDLE -> {
                }
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

    private val binding by viewBinding(VideoViewBinding::bind)

    private var delegate: Delegate? = null
    private var exoPlayer: ExoPlayer? = null

    private var url: String? = null

    private val state = VideoViewMutableState()

    private val playerListener = PlayerListener(WeakReference(this))

    private val lifecycleObserver by lazy {
        val ref = WeakReference(this)
        LifecycleEventObserver { _, event ->
            when (event) {
                Lifecycle.Event.ON_RESUME -> ref.get()?.initPlayer("onResume")
                Lifecycle.Event.ON_PAUSE -> ref.get()?.releasePlayer()
                else -> {}
            }
        }
    }

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    init {
        VideoViewBinding.inflate(layoutInflater, this)
        state.onFullscreenChangedListener = {
            if (state.isFullscreen) {
                openFullscreen()
            } else {
                closeFullscreen()
            }
        }
    }

    fun setDelegate(delegate: Delegate) {
        this.delegate = delegate
    }

    @SuppressLint("UnsafeOptInUsageError")
    fun configure(
        url: String?,
        fullscreen: Boolean
    ) {
        if (!url.isNullOrBlank()) {
            state.isFullscreen = fullscreen
        }
        if (url == this.url) {
            return
        }
        this.url = url
        releasePlayer()
        if (!url.isNullOrBlank()) {
            requireNotNull(findViewTreeLifecycleOwner()).lifecycle.removeObserver(lifecycleObserver)
            requireNotNull(findViewTreeLifecycleOwner()).lifecycle.addObserver(lifecycleObserver)
            initPlayer("New url")
        }
    }

    @SuppressLint("UnsafeOptInUsageError")
    private fun initPlayer(reason: String) {
        val url = this.url
        if (url.isNullOrBlank()) return
        if (exoPlayer != null) {
            return
        }
        binding.videoView.player = ExoPlayer.Builder(context).build().also {
            it.playWhenReady = state.isPlaying
            it.seekTo(state.currentWindow, state.playbackPosition)
            it.setMediaItem(MediaItem.fromUri(Uri.parse(url)), false)
            it.addListener(playerListener)
            it.prepare()
            exoPlayer = it
        }
        if (state.isFirstPlay) {
            binding.videoPlayerPreview.showView(100L)
            binding.videoView.useController = false
        }
        binding.videoView.setShowMultiWindowTimeBar(false)
        binding.videoView.setShowNextButton(false)
        binding.videoView.setShowPreviousButton(false)
        binding.videoView.setShowRewindButton(false)
        binding.videoView.setShowShuffleButton(false)
        binding.videoView.setShowFastForwardButton(false)
        binding.videoView.controllerShowTimeoutMs = 0
        binding.videoView.controllerAutoShow = false
        binding.videoView.setFullscreenButtonClickListener {
            state.isFullscreen = !state.isFullscreen
            delegate?.onFullScreenClick(state)
        }
    }

    private fun releasePlayer() {
        val player = exoPlayer ?: return
        player.removeListener(playerListener)
        state.isPlaying = player.playWhenReady
        state.playbackPosition = player.currentPosition
        state.currentWindow = player.currentMediaItemIndex
        player.stop()
        player.release()
        binding.videoView.player = null
        exoPlayer = null
    }

    private fun openFullscreen() {
        val fullscreenRoot = findFrameLayoutParent()
        binding.videoProgressBar.isVisible = false
        val videoView = binding.videoView
        binding.container.removeView(videoView)
        fullscreenRoot.addView(videoView)
        val params = binding.videoView.layoutParams
        params.width = ViewGroup.LayoutParams.MATCH_PARENT
        params.height = ViewGroup.LayoutParams.MATCH_PARENT
        binding.videoView.layoutParams = params
    }

    @SuppressLint("SourceLockedOrientationActivity")
    private fun closeFullscreen() {
        binding.videoProgressBar.isVisible = true
        val videoView = binding.videoView
        val parent = videoView.parent as? ViewGroup
        parent?.removeView(videoView)
        binding.container.addView(videoView, 0)
        val params = binding.videoView.layoutParams
        params.width = ViewGroup.LayoutParams.MATCH_PARENT
        params.height = 200.dp
        binding.videoView.layoutParams = params
    }

    fun onBackPressed(): Boolean {
        val consumed = state.isFullscreen
        state.isFullscreen = false
        return consumed
    }

    override fun onSaveInstanceState(): Parcelable {
        val superState = super.onSaveInstanceState()
        return state.toSavedState(superState)
    }

    override fun onRestoreInstanceState(state: Parcelable?) {
        val savedState = state as? VideoViewSavedState
        super.onRestoreInstanceState(savedState?.superSavedState ?: state)
        savedState?.let(this.state::applySavedState)
    }

    private fun findFrameLayoutParent(): FrameLayout {
        var currentParent: ViewParent? = parent
        while (currentParent != null) {
            if (currentParent is FrameLayout && currentParent !is NestedScrollView) return currentParent
            currentParent = currentParent.parent
        }
        error("FrameLayout root must be available")
    }
}
