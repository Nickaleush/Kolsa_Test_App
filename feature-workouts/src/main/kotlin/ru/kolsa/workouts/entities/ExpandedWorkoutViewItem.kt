package ru.kolsa.workouts.entities

import ru.kolsa.domain.entity.Workout

data class ExpandedWorkoutViewItem(
    val id: Int,
    val title: String,
    val description: String,
    val type: Int,
    val duration: String,
    val videoUrl: String
)

fun ExpandedWorkoutViewItem(
    workout: Workout,
    videoUrl: String
): ExpandedWorkoutViewItem {
    return ExpandedWorkoutViewItem(
        id = workout.id,
        title = workout.title,
        description = workout.description ?: "Описание тренировки появится позже!",
        type = workout.type,
        duration = workout.duration,
        videoUrl = if (videoUrl.isEmpty()) {
            "https://ref.test.kolsa.ru/example-08.mp4"
        } else {
            "https://ref.test.kolsa.ru${videoUrl}"
        }
    )
}