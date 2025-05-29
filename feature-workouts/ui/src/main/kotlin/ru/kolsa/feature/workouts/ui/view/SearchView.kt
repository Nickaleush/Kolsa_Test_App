package ru.kolsa.feature.workouts.ui.view

import android.content.Context
import android.util.AttributeSet
import androidx.cardview.widget.CardView
import by.kirich1409.viewbindingdelegate.viewBinding
import ru.kolsa.core.ui.edittext.applyViewItem
import ru.kolsa.core.ui.extensions.layoutInflater
import ru.kolsa.core.ui.widgets.TextFieldViewItem
import ru.kolsa.feature.workouts.ui.databinding.SearchItemBinding

internal class SearchView : CardView {

    private val binding by viewBinding(SearchItemBinding::bind)

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)

    init {
        SearchItemBinding.inflate(layoutInflater, this, true)
    }

    fun setUpSearch(item: TextFieldViewItem, onTextChanged: (id: String, newValue: String) -> Unit) {
        binding.editText.applyViewItem(item) { newValue ->
            onTextChanged.invoke(item.id, newValue)
        }
    }
}