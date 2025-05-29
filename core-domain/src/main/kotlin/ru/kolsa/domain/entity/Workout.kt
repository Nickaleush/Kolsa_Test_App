package ru.kolsa.domain.entity

data class Workout(
    val id: Int,
    val title: String,
    val description: String? = null,
    val type: Int,
    val duration: String
)