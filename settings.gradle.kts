pluginManagement {
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        maven { url = uri("https://jitpack.io") }
    }
}

rootProject.name = "Kolsa_Test_App"
include(":app")
include(":app-android")
include(":core-ui")
include(":core-domain")
include(":core-data")
include(":core-di")
include(":feature-workouts")
include(":feature-workouts:businesslogic")
include(":feature-workouts:ui")
 