package ru.kolsa.domain.repository

import ru.kolsa.domain.entity.VideoWorkout
import ru.kolsa.domain.entity.Workout

interface WorkoutRepository {
    suspend fun getWorkouts(): Result<List<Workout>>
    suspend fun getWorkoutVideoUrl(id: String): Result<VideoWorkout>
}