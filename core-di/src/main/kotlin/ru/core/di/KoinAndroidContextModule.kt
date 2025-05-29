package ru.core.di

import org.koin.core.KoinApplication

interface KoinAndroidContextModule {
    fun bindAndroidContext(koinApplication: KoinApplication)
}
