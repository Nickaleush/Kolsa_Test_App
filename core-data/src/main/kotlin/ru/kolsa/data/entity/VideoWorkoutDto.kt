package ru.kolsa.data.entity

import kotlinx.serialization.Serializable
import ru.kolsa.domain.entity.VideoWorkout
import ru.kolsa.domain.entity.Workout

@Serializable
data class VideoWorkoutDto(
    val id: Int,
    val duration: String,
    val link: String
)

fun VideoWorkoutDto.toVideoWorkout() = VideoWorkout(
    id = id,
    duration = duration,
    link = link
)