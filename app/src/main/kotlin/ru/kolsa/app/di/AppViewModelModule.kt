package ru.kolsa.app.di

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import ru.kolsa.app.MainViewModel

val appViewModelModule = module {
    viewModel { MainViewModel() }
}