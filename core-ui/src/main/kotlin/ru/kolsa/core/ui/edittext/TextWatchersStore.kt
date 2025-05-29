package ru.kolsa.core.ui.edittext

import android.widget.TextView
import java.util.WeakHashMap

internal object TextWatchersStore : MutableMap<TextView, TextWatchersList> by WeakHashMap()