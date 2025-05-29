plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
}

android {
    namespace = "ru.kolsa.feature.workouts.ui"
    compileSdk = 34

    defaultConfig {
        minSdk = 24
    }

    buildFeatures {
        viewBinding = true
    }

    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {
    implementation(project(":core-ui"))
    implementation(project(":feature-workouts"))
    implementation(libs.androidx.media.exoplayer)
    implementation(libs.androidx.media.ui)
}