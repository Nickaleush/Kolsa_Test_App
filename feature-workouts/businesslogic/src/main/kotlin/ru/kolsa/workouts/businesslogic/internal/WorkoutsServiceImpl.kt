package ru.kolsa.workouts.businesslogic.internal

import ru.kolsa.domain.entity.VideoWorkout
import ru.kolsa.domain.entity.Workout
import ru.kolsa.domain.repository.WorkoutRepository
import ru.kolsa.workouts.businesslogic.WorkoutsService

internal class WorkoutsServiceImpl(
    private val repository: WorkoutRepository
) : WorkoutsService {
    override suspend fun getWorkouts(): Result<List<Workout>> {
        return repository.getWorkouts()
    }

    override suspend fun getWorkoutVideoUrl(id: String): Result<VideoWorkout> {
        return repository.getWorkoutVideoUrl(id)
    }
}