package ru.kolsa.app.di

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.core.KoinApplication
import ru.core.di.KoinAndroidContextModule

class KoinAndroidContextModuleImpl(
    private val application: Application
) : KoinAndroidContextModule {
    override fun bindAndroidContext(koinApplication: KoinApplication) {
        koinApplication.androidContext(application)
    }
}
