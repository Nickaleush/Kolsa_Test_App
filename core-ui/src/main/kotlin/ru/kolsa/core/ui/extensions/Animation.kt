package ru.kolsa.core.ui.extensions

import android.view.View
import android.view.ViewPropertyAnimator

inline fun View.hideView(duration: Long, crossinline onEnd: () -> Unit = {}): ViewPropertyAnimator {
    return this@hideView.animate().also {
        it.alpha(0f)
        it.setDuration(duration)
        it.withEndAction {
            visibility = View.GONE
            onEnd()
        }
        it.start()
    }
}

fun View.showView(duration: Long) {
    this@showView.animate().run {
        visibility = View.VISIBLE
        alpha(1f)
        start()
        setDuration(duration)
    }
}