package ru.kolsa.data.di

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import okhttp3.MediaType.Companion.toMediaType
import ru.kolsa.data.api.KolsaAPI

fun provideJson(): Json = Json {
    ignoreUnknownKeys = true
    isLenient = true
}

fun provideOkHttpClient(): OkHttpClient =
    OkHttpClient.Builder()
        .addInterceptor(HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        })
        .build()

fun provideRetrofit(json: Json, client: OkHttpClient): Retrofit =
    Retrofit.Builder()
        .baseUrl("https://ref.test.kolsa.ru/")
        .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
        .client(client)
        .build()

fun provideKolsaApi(retrofit: Retrofit): KolsaAPI =
    retrofit.create(KolsaAPI::class.java)