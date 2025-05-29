package ru.core.di

import org.koin.dsl.module
import ru.kolsa.data.di.provideJson
import ru.kolsa.data.di.provideKolsaApi
import ru.kolsa.data.di.provideOkHttpClient
import ru.kolsa.data.di.provideRetrofit
import ru.kolsa.data.repository.WorkoutRepositoryImpl
import ru.kolsa.domain.repository.WorkoutRepository

val appModule = module {
    single { provideJson() }
    single { provideOkHttpClient() }
    single { provideRetrofit(get(), get()) }
    single { provideKolsaApi(get()) }
    single<WorkoutRepository> { WorkoutRepositoryImpl(get()) }
}