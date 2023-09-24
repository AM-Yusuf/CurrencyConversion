// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    id("com.android.application") version "8.1.0" apply false
    id("org.jetbrains.kotlin.android") version "1.9.0" apply false
    id("com.google.devtools.ksp") version "1.9.0-1.0.12" apply false
    //To configure the Hilt Gradle plugin with Gradle's new plugins DSL
    id("com.google.dagger.hilt.android") version "2.48" apply false
}

buildscript {

    dependencies {
        // other plugins...
        classpath("com.google.dagger:hilt-android-gradle-plugin:2.48")

        val kotlinVersion = "1.9.0"
        classpath(kotlin("gradle-plugin", version = kotlinVersion))
    }
}