package ru.kolsa.core.ui.extensions

import android.animation.ValueAnimator
import android.view.MotionEvent
import android.view.View
import android.widget.TextView
import androidx.annotation.ColorInt
import androidx.core.content.res.ResourcesCompat
import com.google.android.material.snackbar.Snackbar
import ru.kolsa.core.ui.R

private const val DEFAULT_BOUND = 75
private const val SCALE_VALUE = 0.95F
private const val VIBRATION_TIME = 5L
private const val VIBRATION_AMPLITUDE = 50

fun View.showSnackBar(
    message: String,
    actionText: String? = null,
    @ColorInt actionTextColor: Int,
    onActionClick: (() -> Unit)? = null
) {
    val snackBar = Snackbar.make(this, message, Snackbar.LENGTH_LONG)
    val snackBarView = snackBar.view
    val snackBarTextView: TextView =
        snackBarView.findViewById(com.google.android.material.R.id.snackbar_text)
    val snackBarActionTextView: TextView =
        snackBarView.findViewById(com.google.android.material.R.id.snackbar_action)

    snackBar.duration = 3000L.toInt()
    configureSnackBarAppearance(snackBarView, snackBarTextView, snackBarActionTextView)

    actionText?.let {
        configureSnackBarAction(snackBar, actionText, actionTextColor, onActionClick)
    }

    snackBarView.post {
        if (isTextEllipsized(snackBarTextView)) {
            enableSnackBarExpansion(snackBarView, snackBarTextView)
        }
        setSnackBarTouchEffects(snackBarView)
    }

    snackBar.show()
}

private fun enableSnackBarExpansion(snackBarView: View, snackBarTextView: TextView) {
    snackBarView.setOnClickListener {
        snackBarTextView.maxLines = Int.MAX_VALUE
        val initialHeight = snackBarView.height
        snackBarView.measure(
            View.MeasureSpec.makeMeasureSpec(snackBarView.width, View.MeasureSpec.EXACTLY),
            View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED)
        )
        val targetHeight = snackBarView.measuredHeight
        animateSnackBarHeight(snackBarView, initialHeight, targetHeight)
    }
}

private fun animateSnackBarHeight(snackBarView: View, initialHeight: Int, targetHeight: Int) {
    ValueAnimator.ofInt(initialHeight, targetHeight).apply {
        addUpdateListener { animation ->
            val value = animation.animatedValue as Int
            val params = snackBarView.layoutParams
            params.height = value
            snackBarView.layoutParams = params
        }
        duration = 300L
        start()
    }
}

private fun configureSnackBarAppearance(
    snackBarView: View,
    snackBarTextView: TextView,
    snackBarActionTextView: TextView
) {
    snackBarView.background = ResourcesCompat.getDrawable(
        snackBarView.resources,
        R.drawable.snackbar_bg,
        null
    )

    val typeface = ResourcesCompat.getFont(snackBarView.context, R.font.unbounded_light)
    snackBarTextView.typeface = typeface
    snackBarActionTextView.typeface = typeface
}

private fun configureSnackBarAction(
    snackBar: Snackbar,
    actionText: String,
    @ColorInt actionTextColor: Int,
    onActionClick: (() -> Unit)?
) {
    snackBar.setActionTextColor(actionTextColor)
        .setAction(actionText) {
            onActionClick?.invoke()
            snackBar.dismiss()
        }
}

private fun setSnackBarTouchEffects(snackBarView: View) {
    snackBarView.setOnTouchListener { view, event ->
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                vibrateDevice(view.context, VIBRATION_TIME, VIBRATION_AMPLITUDE)
                view.animate()
                    .scaleX(SCALE_VALUE)
                    .scaleY(SCALE_VALUE)
                    .setDuration(100L)
                    .start()
            }

            MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> {
                view.animate()
                    .scaleX(1f)
                    .scaleY(1f)
                    .setDuration(100L)
                    .start()
                if (event.action == MotionEvent.ACTION_UP) {
                    view.performClick()
                }
            }
        }
        true
    }
}


private fun isTextEllipsized(textView: TextView): Boolean {
    val layout = textView.layout
    return layout != null &&
            layout.lineCount > 0 &&
            layout.getEllipsisCount(layout.lineCount - 1) > 0
}