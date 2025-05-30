package ru.kolsa.core.ui.video

import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import ru.kolsa.core.ui.extensions.SingleViewModelFactory

interface OrientationManager {

    var isLocked: Boolean

    val isPortrait: Boolean
    fun setPortrait()

    fun setLandscape()

    fun setFragmentRef(fragment: Fragment)
}

val Fragment.orientationManager: OrientationManager
    get() {
        val factory = SingleViewModelFactory {
            OrientationManagerImpl()
        }
        return ViewModelProvider(this, factory)[OrientationManagerImpl::class.java]
    }
