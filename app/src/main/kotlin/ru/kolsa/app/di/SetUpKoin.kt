package ru.kolsa.app.di

import org.koin.core.context.startKoin
import ru.core.di.KoinAndroidContextModule
import ru.core.di.appModule
import ru.kolsa.workouts.businesslogic.di.workoutsBusinessLogicModule
import ru.kolsa.workouts.di.workoutsViewModelModule

class SetUpKoin(
    private val androidContext: KoinAndroidContextModule
) {
    fun setup() {
        startKoin {
            androidContext.bindAndroidContext(this)
            modules(
                appViewModelModule,
                appModule,
                workoutsBusinessLogicModule,
                workoutsViewModelModule
            )
        }
    }
}