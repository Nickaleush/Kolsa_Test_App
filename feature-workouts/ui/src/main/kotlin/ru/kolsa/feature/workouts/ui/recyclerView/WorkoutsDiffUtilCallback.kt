package ru.kolsa.feature.workouts.ui.recyclerView

import android.annotation.SuppressLint
import androidx.recyclerview.widget.DiffUtil
import ru.kolsa.workouts.entities.WorkoutViewItem

internal object WorkoutsDiffUtilCallback : DiffUtil.ItemCallback<Any>() {

    const val CHANGED_WORKOUT_ITEM = "CHANGED_WORKOUT_ITEM"
    const val CHANGED_CHIPS = "CHANGED_EMERGENCY_ANNOUNCEMENT"

    override fun areItemsTheSame(oldItem: Any, newItem: Any): Boolean {
        return oldItem.recyclerViewId() == newItem.recyclerViewId()
    }

    @SuppressLint("DiffUtilEquals")
    override fun areContentsTheSame(oldItem: Any, newItem: Any): Boolean {
        return oldItem == newItem
    }

    override fun getChangePayload(oldItem: Any, newItem: Any): Any {
        val payload = mutableListOf<Any>()
        when {
            oldItem is WorkoutViewItem && newItem is WorkoutViewItem -> {
                if (oldItem != newItem) {
                    payload.add(CHANGED_WORKOUT_ITEM)
                }
            }
            oldItem is MainItem.FiltersItem && newItem is MainItem.FiltersItem -> {
                if (oldItem != newItem) {
                    payload.add(CHANGED_CHIPS)
                }
            }

        }
        return payload
    }
}