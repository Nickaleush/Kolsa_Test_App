package com.example.kolsa_test_app

import android.app.Application
import ru.kolsa.app.di.KoinAndroidContextModuleImpl
import ru.kolsa.app.di.SetUpKoin

class KolsaApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        SetUpKoin(
            KoinAndroidContextModuleImpl(this)
        ).setup()
    }
}