package ru.kolsa.core.ui.extensions

import java.util.UUID

fun String.toUniqueLong(): Long {
    return UUID.nameUUIDFromBytes(toByteArray()).mostSignificantBits
}

fun String.toDurationInMin(): String {
    return when(this) {
        "workout" -> "Силовая тренировка"
        else -> "Длительность $this мин."
    }
}