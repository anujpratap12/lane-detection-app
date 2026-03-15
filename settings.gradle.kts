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
        maven("https://jitpack.io") // Include JitPack for external dependencies
        gradlePluginPortal()
    }
}

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        maven("https://jitpack.io") // Ensure this is included here as well
    }
}

rootProject.name = "Echallan"
include(":app")
include(":libraries:opencv")
project(":libraries:opencv").projectDir = file("C:/libraries-android-sdk/sdk") // Use forward slashes for paths
include(":openCV")
