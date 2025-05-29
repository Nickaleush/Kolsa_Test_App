package ru.kolsa.feature.workouts.ui.view

import android.content.Context
import android.util.AttributeSet
import android.widget.FrameLayout
import by.kirich1409.viewbindingdelegate.viewBinding
import ru.kolsa.core.ui.extensions.layoutInflater
import ru.kolsa.feature.workouts.ui.databinding.TopBlockItemBinding

internal class TopBlockView : FrameLayout {

    private val binding by viewBinding(TopBlockItemBinding::bind)

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)

    init {
        TopBlockItemBinding.inflate(layoutInflater, this, true)
    }

    fun configure(title: String) {
        binding.title.text = title
    }
}