package ru.kolsa.core.ui.edittext

import android.text.Editable
import android.text.TextWatcher
import android.widget.TextView
import androidx.core.widget.doAfterTextChanged
import androidx.core.widget.doOnTextChanged
import ru.kolsa.core.ui.extensions.orFalse
import java.lang.ref.WeakReference

internal class TextWatchersListImpl(
    private val textView: WeakReference<TextView>,
    private val list: MutableList<WeakReference<TextWatcher>> = mutableListOf()
) : TextWatchersList, MutableList<WeakReference<TextWatcher>> by list {

    override fun add(element: WeakReference<TextWatcher>): Boolean {
        return list.add(element).also {
            element.get()?.addToTextView()
        }
    }

    override fun remove(element: WeakReference<TextWatcher>): Boolean {
        return list.remove(element).also {
            element.get()?.removeFromTextView()
        }
    }

    override fun removeAll(elements: Collection<WeakReference<TextWatcher>>): Boolean {
        elements.forEach {
            it.get()?.removeFromTextView()
        }
        return list.removeAll(elements)
    }

    override fun clear() {
        list.forEach {
            it.get()?.removeFromTextView()
        }
        list.clear()
    }

    override fun addOnTextChanged(action: (text: CharSequence?, start: Int, before: Int, count: Int) -> Unit): Boolean {
        return textView.get()?.doOnTextChanged(action)?.let {
            list.add(WeakReference(it))
        }.orFalse()
    }

    override fun addAfterTextChanged(block: (Editable) -> Unit): Boolean {
        return textView.get()?.doAfterTextChanged { it?.let(block) }?.let {
            list.add(WeakReference(it))
        }.orFalse()
    }

    private fun TextWatcher.addToTextView() {
        textView.get()?.addTextChangedListener(this)
    }
    private fun TextWatcher.removeFromTextView() {
        textView.get()?.removeTextChangedListener(this)
    }
}