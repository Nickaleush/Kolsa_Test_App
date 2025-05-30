package ru.kolsa.feature.workouts.ui.view

import android.content.Context
import android.util.AttributeSet
import android.widget.FrameLayout
import by.kirich1409.viewbindingdelegate.viewBinding
import com.airbnb.lottie.LottieDrawable
import ru.kolsa.core.ui.extensions.dpToPx
import ru.kolsa.core.ui.extensions.layoutInflater
import ru.kolsa.feature.workouts.ui.R
import ru.kolsa.feature.workouts.ui.databinding.WorkoutItemBinding
import ru.kolsa.workouts.entities.WorkoutViewItem

class WorkoutView : FrameLayout {

    data class ItemClickResult(
        val item: WorkoutViewItem
    )

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)

    private val binding by viewBinding(WorkoutItemBinding::bind)

    var onWorkoutClick: ((ItemClickResult) -> Unit)? = null

    init {
        WorkoutItemBinding.inflate(layoutInflater, this)
    }

    fun configure(
        item: WorkoutViewItem,
    ) = with(binding) {
        binding.title.text = item.title
        binding.workoutTypeImage.setImageResource(
            when (item.type) {
                1 -> R.drawable.workout_type_1
                2 -> R.drawable.workout_type_2
                3 -> R.drawable.workout_type_3
                else -> R.drawable.workout_type_3
            }
        )
        binding.duration.text = item.duration
        binding.durationAnim.progress = 0F
        binding.durationAnim.playAnimation()
        binding.durationAnim.repeatMode = LottieDrawable.REVERSE

        val (animFile, animSize, animMarginEnd) = when (item.duration) {
            "workout" -> Triple("workout_anim.json", 30, 10)
            else -> Triple("fire_anim.json", 50, 0)
        }

        binding.durationAnim.setAnimation(animFile)

        val layoutParams = binding.durationAnim.layoutParams as MarginLayoutParams
        layoutParams.width = animSize.dpToPx()
        layoutParams.height = animSize.dpToPx()
        layoutParams.marginEnd = animMarginEnd.dpToPx()
        binding.durationAnim.layoutParams = layoutParams
        root.setOnClickListener {
            onWorkoutClick?.invoke(ItemClickResult(item))
        }
    }
}