package ru.kolsa.data.entity

import kotlinx.serialization.Serializable
import ru.kolsa.domain.entity.Workout

@Serializable
data class WorkoutDto(
    val id: Int,
    val title: String,
    val description: String? = null,
    val type: Int,
    val duration: String
)

fun WorkoutDto.toWorkout() = Workout(
    id = id,
    title = title,
    description = description,
    type = type,
    duration = duration
)