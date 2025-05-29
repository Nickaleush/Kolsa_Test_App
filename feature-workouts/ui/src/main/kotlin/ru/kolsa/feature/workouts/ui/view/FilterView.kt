package ru.kolsa.feature.workouts.ui.view

import android.content.Context
import android.util.AttributeSet
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import by.kirich1409.viewbindingdelegate.viewBinding
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import ru.kolsa.core.ui.extensions.layoutInflater
import ru.kolsa.feature.workouts.ui.R
import ru.kolsa.feature.workouts.ui.databinding.FilterItemBinding

internal class FilterView : ChipGroup {

    private val binding by viewBinding(FilterItemBinding::bind)

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)

    init {
        FilterItemBinding.inflate(layoutInflater, this, true)
    }

    fun setChipItems(types: List<Int>, selectedType: Int?, onTypeSelected: (Int?) -> Unit) {
        binding.chipGroup.removeAllViews()
        types.forEach { type ->
            val chip = Chip(context).apply {
                text = when (type) {
                    1 -> "Тренировка"
                    2 -> "Эфир"
                    3 -> "Комплекс"
                    else -> "Другое"
                }
                background = ContextCompat.getDrawable(
                    context,
                    R.drawable.chip_bg
                )
                setTextAppearance(R.style.chipTextAppearance)
                isCheckable = true
                isChecked = type == selectedType
                setOnClickListener {
                    onTypeSelected(if (isChecked) type else null)
                }
            }
            binding.chipGroup.addView(chip)
        }
    }
}