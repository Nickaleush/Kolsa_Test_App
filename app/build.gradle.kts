plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
}

android {
    namespace = "ru.kolsa.app"
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
    implementation(project(":core-data"))
    implementation(project(":feature-workouts:ui"))
    implementation(project(":feature-workouts"))
    implementation(project(":feature-workouts:businesslogic"))
    implementation(project(":core-di"))
    implementation(libs.koin.android)
}