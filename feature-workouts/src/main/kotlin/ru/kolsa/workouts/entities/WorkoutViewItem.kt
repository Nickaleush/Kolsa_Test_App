package ru.kolsa.workouts.entities

import ru.kolsa.core.ui.extensions.toDurationInMin
import ru.kolsa.domain.entity.Workout

data class WorkoutViewItem(
    val id: Int,
    val title: String,
    val description: String,
    val type: Int,
    val duration: String
)

fun WorkoutViewItem(embeddedWorkout: Workout): WorkoutViewItem {
    return WorkoutViewItem(
        id = embeddedWorkout.id,
        title = embeddedWorkout.title,
        description = embeddedWorkout.description.orEmpty(),
        type = embeddedWorkout.type,
        duration = embeddedWorkout.duration.toDurationInMin()
    )
}