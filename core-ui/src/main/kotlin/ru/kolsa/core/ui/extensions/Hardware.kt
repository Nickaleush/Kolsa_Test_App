package ru.kolsa.core.ui.extensions

import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import androidx.core.content.ContextCompat

@Suppress("DEPRECATION")
@SuppressLint("MissingPermission")
fun vibrateDevice(context: Context, time: Long, amplitude: Int) {
    val vibrator = ContextCompat.getSystemService(context, Vibrator::class.java)
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        vibrator?.vibrate(
            VibrationEffect.createOneShot(
                time,
                amplitude
            )
        )
    } else {
        vibrator?.vibrate(time)
    }
}