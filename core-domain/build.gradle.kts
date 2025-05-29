plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
}

android {
    namespace = "com.example.core.domain"
    compileSdk = 34
    kotlinOptions {
        jvmTarget = "1.8"
    }

    dependencies {
        api(libs.kotlinxCoroutines)
    }
}