package ru.kolsa.core.ui.video

import android.annotation.SuppressLint
import android.content.pm.ActivityInfo
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.lang.ref.WeakReference

internal class OrientationManagerImpl : ViewModel(),
    OrientationManager {

    private var fragmentRef: WeakReference<out Fragment>? = null

    private companion object {
        const val UNSPECIFIED_ORIENTATION_ENABLING_DELAY = 1000L
    }

    private var orientationChangeJob: Job? = null
    private val activity: FragmentActivity?
        get() = fragmentRef?.get()?.activity

    override var isLocked: Boolean = false
        @SuppressLint("SourceLockedOrientationActivity")
        set(value) {
            field = value
            if (value) {
                activity?.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
            } else {
                activity?.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED
            }
        }

    override val isPortrait: Boolean
        get() = fragmentRef?.get()?.resources?.configuration?.orientation == ActivityInfo.SCREEN_ORIENTATION_PORTRAIT

    override fun setPortrait() {
        set(true)
    }

    override fun setLandscape() {
        set(false)
    }

    override fun setFragmentRef(fragment: Fragment) {
        fragmentRef = WeakReference(fragment)
    }

    private fun set(isPortrait: Boolean) {
        if (isLocked) return
        orientationChangeJob?.cancel()
        orientationChangeJob = fragmentRef?.get()?.lifecycleScope?.launch {
            val orientation = if (isPortrait) {
                ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
            } else {
                ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
            }
            activity?.requestedOrientation = orientation
            delay(UNSPECIFIED_ORIENTATION_ENABLING_DELAY)
            activity?.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED
        }
    }

    override fun onCleared() {
        set(true)
    }
}
