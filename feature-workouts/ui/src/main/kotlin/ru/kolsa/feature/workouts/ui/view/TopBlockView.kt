package ru.kolsa.feature.workouts.ui.view

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator
import android.widget.FrameLayout
import by.kirich1409.viewbindingdelegate.viewBinding
import ru.kolsa.core.ui.extensions.layoutInflater
import ru.kolsa.feature.workouts.ui.databinding.TopBlockItemBinding

internal class TopBlockView : FrameLayout {

    private val binding by viewBinding(TopBlockItemBinding::bind)

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)

    var onKolsaLogoClick: (() -> Unit)? = null

    init {
        TopBlockItemBinding.inflate(layoutInflater, this, true)
    }

    fun configure(title: String) {
        binding.kolsaLogo.setOnClickListener {
            onKolsaLogoClick?.invoke()
        }
        binding.title.text = title
        startZoomAnimation(binding.kolsaLogo)
    }

    private fun startZoomAnimation(view: View) {
        val scaleUpX = ObjectAnimator.ofFloat(view, View.SCALE_X, 1f, 1.2f)
        val scaleUpY = ObjectAnimator.ofFloat(view, View.SCALE_Y, 1f, 1.2f)
        val scaleDownX = ObjectAnimator.ofFloat(view, View.SCALE_X, 1.2f, 1f)
        val scaleDownY = ObjectAnimator.ofFloat(view, View.SCALE_Y, 1.2f, 1f)

        val scaleUp = AnimatorSet().apply {
            playTogether(scaleUpX, scaleUpY)
            duration = 300
        }

        val scaleDown = AnimatorSet().apply {
            playTogether(scaleDownX, scaleDownY)
            duration = 300
        }

        val pulse = AnimatorSet().apply {
            playSequentially(scaleUp, scaleDown)
            startDelay = 1000
            addListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator) {
                    animation.start()
                }
            })
        }
        pulse.start()
    }
}