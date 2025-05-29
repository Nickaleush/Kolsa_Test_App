plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
}

android {
    namespace = "ru.kolsa.core.ui"
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
    api(libs.androidx.core.ktx)
    api(libs.androidx.appcompat)
    api(libs.androidx.fragment.ktx)
    api(libs.viewbinding.delegate)
    api(libs.material)
    api(libs.lottie)
    api(libs.androidx.activity)
    api(libs.androidx.navigation.fragmentktx)
    api(libs.androidx.navigation.uiktx)
    api(libs.androidx.lifecycle.viewmodel)
    api(libs.androidx.lifecycle.runtimektx)
    api(libs.material)
    api(libs.koin.android)
    api(libs.androidx.core.ktx)
    api(libs.android.thirdpartyui.lottieSwipeRefreshLayout)
    api (libs.faltenreich.skeletonlayout)
    api(libs.android.thirdpartyui.advancedcardview)
    api(libs.android.thirdpartyui.inboxrecyclerview)

    androidTestImplementation(libs.androidx.junit)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}