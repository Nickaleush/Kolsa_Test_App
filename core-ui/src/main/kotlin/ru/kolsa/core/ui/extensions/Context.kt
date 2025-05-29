package ru.kolsa.core.ui.extensions

import android.annotation.SuppressLint
import android.app.ActivityManager
import android.content.ClipboardManager
import android.content.Context
import android.content.res.ColorStateList
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.os.Vibrator
import android.os.VibratorManager
import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.annotation.ColorRes
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.content.ContextCompat

/**
 * More convenient obtaining of [LayoutInflater]
 * */
val Context.layoutInflater: LayoutInflater
    get() = LayoutInflater.from(this)

val View.layoutInflater: LayoutInflater
    get() = context.layoutInflater
/**
 * More convenient obtaining of [InputMethodManager]
 * */
@Suppress("DontForceCast")
val Context.inputMethodManager: InputMethodManager
    get() = requireNotNull(getSystemService(Context.INPUT_METHOD_SERVICE)) as InputMethodManager

/**
 * More convenient obtaining of [ConnectivityManager]
 * */
@Suppress("DontForceCast")
val Context.connectivityManager: ConnectivityManager
    get() = requireNotNull(getSystemService(Context.CONNECTIVITY_SERVICE)) as ConnectivityManager

/**
 * More convenient obtaining of [WindowManager]
 * */
@Suppress("DontForceCast")
val Context.windowManager: WindowManager
    get() = requireNotNull(getSystemService(Context.WINDOW_SERVICE)) as WindowManager

/**
 * More convenient obtaining of [ClipboardManager]
 * */
@Suppress("DontForceCast")
val Context.clipBoardManager: ClipboardManager
    get() = requireNotNull(getSystemService(Context.CLIPBOARD_SERVICE)) as ClipboardManager

/**
 * More convenient obtaining of [Vibrator]
 * */
@Suppress("DontForceCast")
val Context.vibrator: Vibrator
    get() = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
        val vibratorManager =
            requireNotNull(getSystemService(Context.VIBRATOR_MANAGER_SERVICE)) as VibratorManager
        vibratorManager.defaultVibrator
    } else {
        @Suppress("DEPRECATION")
        requireNotNull(getSystemService(Context.VIBRATOR_SERVICE)) as Vibrator
    }

/**
 * More convenient obtaining of [ActivityManager]
 * */
@Suppress("DontForceCast")
val Context.activityManager: ActivityManager
    get() = requireNotNull(getSystemService(Context.ACTIVITY_SERVICE)) as ActivityManager

/**
 * More convenient way to show toast
 * **/
fun Context.showToast(text: String, length: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(this, text, length).show()
}

fun Context.getColorCompat(@ColorRes resId: Int): Int {
    return ContextCompat.getColor(this, resId)
}

fun Context.getColorStateListCompatOrNull(@ColorRes resId: Int): ColorStateList? {
    return AppCompatResources.getColorStateList(this, resId)
}

@Suppress("DEPRECATION")
@SuppressLint("MissingPermission")
fun Context.isNetworkAvailable(): Boolean {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        val nw = connectivityManager.activeNetwork
        val actNw = connectivityManager.getNetworkCapabilities(nw)
        when {
            actNw?.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) == true -> true
            actNw?.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) == true -> true
            actNw?.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) == true -> true
            actNw?.hasTransport(NetworkCapabilities.TRANSPORT_BLUETOOTH) == true -> true
            else -> false
        }
    } else {
        connectivityManager.activeNetworkInfo?.isConnected ?: false
    }
}
