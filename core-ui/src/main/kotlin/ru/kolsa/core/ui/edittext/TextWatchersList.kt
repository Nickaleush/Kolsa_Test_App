package ru.kolsa.core.ui.edittext

import android.text.Editable
import android.text.TextWatcher
import java.lang.ref.WeakReference

public interface TextWatchersList : MutableList<WeakReference<TextWatcher>> {

    public fun addOnTextChanged(
        action: (
            text: CharSequence?,
            start: Int,
            before: Int,
            count: Int
        ) -> Unit
    ): Boolean

    public fun addAfterTextChanged(block: (Editable) -> Unit): Boolean
}