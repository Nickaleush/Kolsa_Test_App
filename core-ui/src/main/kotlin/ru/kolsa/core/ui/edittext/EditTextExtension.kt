package ru.kolsa.core.ui.edittext

import android.widget.EditText
import ru.kolsa.core.ui.widgets.TextFieldViewItem

fun EditText.applyViewItem(
    item: TextFieldViewItem,
    onTextChanged: (String) -> Unit,
) {
    val currentText = text.toString()
    if (textWatchers.isEmpty()) {
        textWatchers.addAfterTextChanged {
            onTextChanged(it.toString())
        }
    }
    if (currentText != item.content) {
        textWatchers.clear()
        var currentSelection = selectionStart
        val isSelectionAtLastPosition = currentSelection == text.length
        setText(item.content)
        currentSelection = if (isSelectionAtLastPosition) {
            item.content.length
        } else {
            currentSelection.coerceAtMost(item.content.length)
        }
        if (isFocused) {
            setSelection(currentSelection)
        }
        textWatchers.addAfterTextChanged {
            onTextChanged(it.toString())
        }
    }
    hint = item.placeholder.orEmpty()
    isEnabled = item.isEnabled
}