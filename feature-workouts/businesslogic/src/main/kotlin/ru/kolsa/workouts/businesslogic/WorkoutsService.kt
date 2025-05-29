package ru.kolsa.workouts.businesslogic

import ru.kolsa.domain.entity.VideoWorkout
import ru.kolsa.domain.entity.Workout

interface WorkoutsService {
    suspend fun getWorkouts(): Result<List<Workout>>

    suspend fun getWorkoutVideoUrl(id: String): Result<VideoWorkout>
}