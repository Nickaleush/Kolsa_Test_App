package ru.kolsa.core.ui.edittext

import android.widget.TextView
import java.lang.ref.WeakReference

public val TextView.textWatchers: TextWatchersList
    get() = TextWatchersStore.getOrPut(this) { TextWatchersListImpl(WeakReference(this)) }