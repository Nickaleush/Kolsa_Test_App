package ru.kolsa.workouts.businesslogic.di

import org.koin.dsl.module
import ru.kolsa.workouts.businesslogic.WorkoutsService
import ru.kolsa.workouts.businesslogic.internal.WorkoutsServiceImpl

val workoutsBusinessLogicModule = module {
    factory<WorkoutsService> { WorkoutsServiceImpl(get()) }
}