plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    kotlin("plugin.serialization") version "1.9.0"
}

android {
    namespace = "com.example.core.data"
    compileSdk = 34
    defaultConfig {
        minSdk = 24
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {
    implementation(project(":core-domain"))
    api(libs.retrofit)
    api(libs.okhttp)
    api(libs.logging.interceptor)
    api(libs.kotlinxSerialization)
    api(libs.retrofit2.kotlinx.serialization.converter)
}