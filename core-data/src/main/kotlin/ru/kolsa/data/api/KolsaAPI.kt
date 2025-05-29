package ru.kolsa.data.api

import retrofit2.http.GET
import retrofit2.http.Query
import ru.kolsa.data.entity.VideoWorkoutDto
import ru.kolsa.data.entity.WorkoutDto

interface KolsaAPI {
    @GET("get_workouts")
    suspend fun getWorkouts(): List<WorkoutDto>

    @GET("get_video")
    suspend fun getWorkoutVideoUrl(
        @Query("id") workoutId: Int
    ): VideoWorkoutDto
}