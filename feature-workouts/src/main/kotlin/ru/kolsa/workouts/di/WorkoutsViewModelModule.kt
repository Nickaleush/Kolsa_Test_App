package ru.kolsa.workouts.di

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import ru.kolsa.workouts.WorkoutsViewModel

val workoutsViewModelModule = module {
    viewModel { WorkoutsViewModel(get()) }
}