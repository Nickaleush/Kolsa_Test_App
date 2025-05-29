package ru.kolsa.core.ui.extensions

import android.content.res.Resources

val Int.dp: Int
    get() = (this * Resources.getSystem().displayMetrics.density).toInt()

fun Int.dpToPx(): Int {
    return (this * Resources.getSystem().displayMetrics.density).toInt()
}

public fun Boolean?.orFalse(): Boolean = this ?: false
public fun Boolean?.orTrue(): Boolean = this ?: true