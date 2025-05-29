package ru.kolsa.data.repository

import ru.kolsa.data.api.KolsaAPI
import ru.kolsa.data.entity.WorkoutDto
import ru.kolsa.data.entity.toVideoWorkout
import ru.kolsa.data.entity.toWorkout
import ru.kolsa.domain.entity.VideoWorkout
import ru.kolsa.domain.entity.Workout
import ru.kolsa.domain.repository.WorkoutRepository

class WorkoutRepositoryImpl(
    private val api: KolsaAPI
) : WorkoutRepository {
    override suspend fun getWorkouts(): Result<List<Workout>> = runCatching {
        val workoutsDto: List<WorkoutDto> = api.getWorkouts()
        workoutsDto.map { it.toWorkout() }
    }

    override suspend fun getWorkoutVideoUrl(id: String): Result<VideoWorkout> = runCatching {
        api.getWorkoutVideoUrl(id.toInt()).toVideoWorkout()
    }
}