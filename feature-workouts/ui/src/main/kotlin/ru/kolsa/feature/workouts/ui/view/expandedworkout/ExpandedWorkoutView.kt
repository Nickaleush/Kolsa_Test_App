package ru.kolsa.feature.workouts.ui.view.expandedworkout

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.widget.FrameLayout
import ru.kolsa.core.ui.extensions.layoutInflater
import ru.kolsa.core.ui.extensions.toDurationInMin
import ru.kolsa.feature.workouts.ui.databinding.ExpandedWorkoutViewBinding
import ru.kolsa.workouts.entities.ExpandedWorkoutViewItem

class ExpandedWorkoutView : FrameLayout {

    constructor(context: Context) : super(context)
    constructor(context: Context, attributeSet: AttributeSet?) : super(context, attributeSet)
    constructor(context: Context, attributeSet: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attributeSet,
        defStyleAttr
    )

    private val binding = ExpandedWorkoutViewBinding.inflate(layoutInflater, this)
    private var workoutItem: ExpandedWorkoutViewItem? = null

    var actionClickListener: ((ExpandedWorkoutViewClickAction) -> Unit)? = null

    init {
        binding.toolbar.setNavigationOnClickListener {
            val property = this.workoutItem ?: return@setNavigationOnClickListener
            actionClickListener?.invoke(ExpandedWorkoutViewClickAction.Toolbar(property))
        }
    }

    fun releasePlayer() {
        binding.videoView.releasePlayer()
    }

    fun updateWorkoutItem(
        item: ExpandedWorkoutViewItem
    ) {
        this.workoutItem = item
        binding.title.text = item.title
        binding.description.text = item.description
        binding.duration.text = item.duration.toDurationInMin()
        Log.d("ExpandedWorkoutView", "video url is ${item.videoUrl}")
        binding.videoView.configure(
            item.videoUrl
        )
    }
}