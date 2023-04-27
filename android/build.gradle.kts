import com.android.build.gradle.AppExtension
import com.android.build.gradle.AppPlugin
import com.android.build.gradle.BaseExtension
import com.android.build.gradle.LibraryExtension
import com.android.build.gradle.LibraryPlugin
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("com.android.application") version "7.4.1" apply false
    id("com.android.library") version "7.4.1" apply false
    id("org.jetbrains.kotlin.android") version "1.8.10" apply false
    id("org.jetbrains.kotlin.jvm") version "1.8.10" apply false
    kotlin("plugin.serialization") version "1.8.10"
}

subprojects {
    project.plugins.applyBaseConfig(project)
}

buildscript {
    dependencies {
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.4.21")
        classpath("com.google.gms:google-services:4.3.15")
    }
}

allprojects {
    repositories {
        gradlePluginPortal()
        google()
        maven { setUrl("https://jitpack.io") }
        mavenCentral()
    }
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}

tasks.withType<Test>().configureEach {
    useJUnitPlatform()
}

fun BaseExtension.baseConfig() {

    compileSdkVersion(AppConfig.compileSdk)

    defaultConfig.apply {
        minSdk = AppConfig.minSdk
        targetSdk = AppConfig.targetSdk
        versionCode = AppConfig.versionCode
        versionName = AppConfig.versionName
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        testOptions {
            unitTests.all {
                it.useJUnitPlatform()
            }
        }
    }

    compileOptions.apply {
        sourceCompatibility = AppConfig.CompileOptions.javaSourceCompatibility
        targetCompatibility = AppConfig.CompileOptions.javaSourceCompatibility
    }

    buildTypes.apply {
        getByName("release") {
            buildConfigField("String", "BaseUrl", "http://10.0.2.2:8080/")
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "${project.rootDir}/app/proguard-rules.pro"
            )
        }

        getByName("debug") {
            buildConfigField("String", "BaseUrl", "\"http://10.0.2.2:8080/\"")
        }
    }

    tasks.withType<KotlinCompile> {
        kotlinOptions {
            jvmTarget = AppConfig.CompileOptions.kotlinJvmTarget
        }
    }

    buildFeatures.apply {
        compose = true
    }

    composeOptions.apply {
        kotlinCompilerExtensionVersion = "1.4.2"
    }

    packagingOptions {
        resources.excludes += "DebugProbesKt.bin"
    }
}

fun PluginContainer.applyBaseConfig(project: Project) {
    whenPluginAdded {
        when (this) {
            is AppPlugin -> {
                project.extensions
                    .getByType<AppExtension>()
                    .apply { baseConfig() }
            }
            is LibraryPlugin -> {
                project.extensions
                    .getByType<LibraryExtension>()
                    .apply { baseConfig() }
            }
        }
    }
}
