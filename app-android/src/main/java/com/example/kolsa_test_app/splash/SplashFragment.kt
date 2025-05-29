package com.example.kolsa_test_app.splash

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.animation.PropertyValuesHolder
import android.animation.ValueAnimator
import android.os.Bundle
import android.view.View
import android.view.animation.AccelerateInterpolator
import androidx.activity.addCallback
import androidx.core.animation.doOnEnd
import androidx.core.animation.doOnStart
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.kolsa_test_app.R
import com.example.kolsa_test_app.databinding.SplashScreenBinding
import ru.kolsa.app.MainContract
import ru.kolsa.app.MainViewModel
import ru.kolsa.core.ui.extensions.showView
import ru.kolsa.core.ui.extensions.vibrateDevice

class SplashFragment : Fragment(R.layout.splash_screen) {

    companion object {
        private const val DEFAULT_PULSE_DURATION = 200L
        private const val DEFAULT_EXPAND_DURATION = 400L
        private const val DEFAULT_SCALE_VALUE = 1F
        private const val EXPAND_SCALE = 10f

        private const val FIRST_VIBRATION_DURATION = 5L
        private const val FIRST_VIBRATION_AMPLITUDE = 2

        private const val SECOND_VIBRATION_DURATION = 10L
        private const val SECOND_VIBRATION_AMPLITUDE = 20

        private const val FINAL_VIBRATION_DURATION = 50L
        private const val FINAL_VIBRATION_AMPLITUDE = 100
    }

    private val binding by viewBinding(SplashScreenBinding::bind)
    private val viewModel by activityViewModels<MainViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, true) {}
        prepareInitialScales()
        startSplashAnimation()
    }

    private fun prepareInitialScales() {
        with(binding) {
            logoImage.apply {
                scaleX = DEFAULT_SCALE_VALUE
                scaleY = DEFAULT_SCALE_VALUE
                alpha = DEFAULT_SCALE_VALUE
            }
            circleView.apply {
                scaleX = DEFAULT_SCALE_VALUE
                scaleY = DEFAULT_SCALE_VALUE
            }
        }
    }

    private fun startSplashAnimation() {
        AnimatorSet().apply {
            playSequentially(
                createPulseAnimation(phase = 0),
                createPulseAnimation(phase = 1),
                createExpandAnimation()
            )
            start()
        }
    }

    private fun createPulseAnimation(phase: Int): AnimatorSet {
        val scaleUp = createScaleAnimator(1f, 1.2f)
        val scaleDown = createScaleAnimator(1.2f, 1f)

        scaleUp.doOnStart {
            when (phase) {
                0 -> vibrateDevice(
                    requireContext(),
                    FIRST_VIBRATION_DURATION,
                    FIRST_VIBRATION_AMPLITUDE
                )

                1 -> vibrateDevice(
                    requireContext(),
                    SECOND_VIBRATION_DURATION,
                    SECOND_VIBRATION_AMPLITUDE
                )
            }
        }

        return AnimatorSet().apply {
            playSequentially(scaleUp, scaleDown)
        }
    }

    private fun createExpandAnimation(): ValueAnimator {
        return ValueAnimator.ofFloat(1f, EXPAND_SCALE).apply {
            duration = DEFAULT_EXPAND_DURATION
            interpolator = AccelerateInterpolator()

            addUpdateListener { anim ->
                val value = anim.animatedValue as Float
                with(binding) {
                    circleView.scaleX = value
                    circleView.scaleY = value
                    logoImage.scaleX = value
                    logoImage.scaleY = value
                    logoImage.alpha = 1f - (value - 1f) / (EXPAND_SCALE - 1f)
                }
            }

            doOnStart {
                binding.circleView.showView(100)
                vibrateDevice(requireContext(), FINAL_VIBRATION_DURATION, FINAL_VIBRATION_AMPLITUDE)
            }

            doOnEnd {
                viewModel.intent(MainContract.Intent.SplashScreenEnded)
            }
        }
    }

    private fun createScaleAnimator(from: Float, to: Float): ObjectAnimator {
        return ObjectAnimator.ofPropertyValuesHolder(
            binding.logoImage,
            PropertyValuesHolder.ofFloat(View.SCALE_X, from, to),
            PropertyValuesHolder.ofFloat(View.SCALE_Y, from, to)
        ).apply {
            this.duration = DEFAULT_PULSE_DURATION
        }
    }
}