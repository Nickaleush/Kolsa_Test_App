package ru.kolsa.core.ui.extensions

import android.animation.Animator
import android.animation.Animator.AnimatorListener
import com.airbnb.lottie.LottieAnimationView

inline fun LottieAnimationView.addLottieAnimationListener(
    crossinline onAnimationStart: (Animator) -> Unit = {},
    crossinline onAnimationEnd: (Animator) -> Unit = {},
    crossinline onAnimationCancel: (Animator) -> Unit = {},
    crossinline onAnimationRepeat: (Animator) -> Unit = {},
) {
    return addAnimatorListener(object : AnimatorListener {
        override fun onAnimationStart(animation: Animator) {
            onAnimationStart(animation)
        }

        override fun onAnimationEnd(animation: Animator) {
            onAnimationEnd(animation)
        }

        override fun onAnimationCancel(animation: Animator) {
            onAnimationCancel(animation)
        }

        override fun onAnimationRepeat(animation: Animator) {
            onAnimationRepeat(animation)
        }
    })
}

inline fun LottieAnimationView.doOnLottieAnimationEnd(crossinline block: (Animator) -> Unit) {
    addLottieAnimationListener(onAnimationEnd = block)
}

inline fun LottieAnimationView.doOnLottieAnimationStart(crossinline block: (Animator) -> Unit) {
    addLottieAnimationListener(onAnimationStart = block)
}

inline fun LottieAnimationView.doOnLottieAnimationRepeat(crossinline block: (Animator) -> Unit) {
    addLottieAnimationListener(onAnimationRepeat = block)
}

inline fun LottieAnimationView.doOnLottieAnimationCancel(crossinline block: (Animator) -> Unit) {
    addLottieAnimationListener(onAnimationCancel = block)
}