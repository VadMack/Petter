object Libs {

    private object Versions {
        const val coroutines = "1.6.1"
        const val serialization = "1.5.0-RC"
        const val coil = "2.0.0-rc03"
        const val compressor = "3.0.1"
        const val viewBinding = "1.5.3"
        const val okHttp = "4.9.3"
        const val retrofit = "2.9.0"
        const val retrofitSerializationConverter = "0.8.0"
        const val koin = "3.2.0"
        const val leakCanary = "2.9.1"
        const val dagger2 = "2.17"
    }

    const val coroutines = "org.jetbrains.kotlinx:kotlinx-coroutines-android:${Versions.coroutines}"
    const val serialization = "org.jetbrains.kotlinx:kotlinx-serialization-json:${Versions.serialization}"

    const val dagger2 = "com.google.dagger:dagger:${Versions.dagger2}"
    const val dagger2Compiler = "com.google.dagger:dagger-compiler:${Versions.dagger2}"

    const val coil = "io.coil-kt:coil:${Versions.coil}"
    const val viewBinding = "com.github.kirich1409:viewbindingpropertydelegate:${Versions.viewBinding}"

    const val okHttp = "com.squareup.okhttp3:okhttp:${Versions.okHttp}"
    const val okHttpLoggingInterceptor = "com.squareup.okhttp3:logging-interceptor:${Versions.okHttp}"

    const val retrofit = "com.squareup.retrofit2:retrofit:${Versions.retrofit}"
    const val retrofitSerializationConverter = "com.jakewharton.retrofit:retrofit2-kotlinx-serialization-converter:${Versions.retrofitSerializationConverter}"
    const val retrofitGsonConverter = "com.squareup.retrofit2:converter-gson:${Versions.retrofit}"

    const val koin = "io.insert-koin:koin-android:${Versions.koin}"

    const val leakCanary = "com.squareup.leakcanary:leakcanary-android:${Versions.leakCanary}"
}

object Tests {
    private object Versions {
        const val koTest = "5.3.0"
        const val turbine = "0.8.0"
    }

    const val koTest = "io.kotest:kotest-runner-junit5:${Versions.koTest}"
    const val koTestAssertions = "io.kotest:kotest-assertions-core:${Versions.koTest}"
    const val koTestFramework = "io.kotest:kotest-framework-api-jvm:${Versions.koTest}"

    const val turbine = "app.cash.turbine:turbine:${Versions.turbine}"
}

object View {

    private object Versions {
        const val recyclerView = "1.2.1"
        const val constraintLayout = "2.1.3"
        const val viewPager = "1.0.0"
    }

    const val recyclerView = "androidx.recyclerview:recyclerview:${Versions.recyclerView}"
    const val viewPager = "androidx.viewpager2:viewpager2:${Versions.viewPager}"
    const val constraintLayout =
        "androidx.constraintlayout:constraintlayout:${Versions.constraintLayout}"
}


object AndroidX {

    private object Versions {
        const val coreKtx = "1.9.0"
        const val fragmentKtx = "1.4.1"
        const val appCompat = "1.4.1"
        const val material = "1.5.0"
        const val lifecycleRuntimeKtx = "2.5.1"
        const val lifecycleViewModelKtx = "2.4.1"
        const val lifecycleExtensions = "2.2.0"
        const val composeUi = "1.3.3"
        const val composeMaterial = "1.3.1"
        const val composeFoundation = "1.3.1"
        const val composeUiTooling = "1.3.2"
        const val composeActivity = "1.6.1"
        const val composeViewModel = "2.5.1"
    }

    const val composeUi = "androidx.compose.ui:ui:${Versions.composeUi}"
    const val composeMaterial = "androidx.compose.material:material:${Versions.composeMaterial}"
    const val composeFoundation = "androidx.compose.foundation:foundation:${Versions.composeFoundation}"
    const val composePreview = "androidx.compose.ui:ui-tooling-preview:${Versions.composeUiTooling}"
    const val composeUiTooling = "androidx.compose.ui:ui-tooling:${Versions.composeUiTooling}"
    const val composeActivity = "androidx.activity:activity-compose:${Versions.composeActivity}"
    const val composeViewModel = "androidx.lifecycle:lifecycle-viewmodel-compose:${Versions.composeViewModel}"

    const val coreKtx = "androidx.core:core-ktx:${Versions.coreKtx}"
    const val fragmentKtx = "androidx.fragment:fragment-ktx:${Versions.fragmentKtx}"

    const val appCompat = "androidx.appcompat:appcompat:${Versions.appCompat}"
    const val material = "com.google.android.material:material:${Versions.material}"
    const val lifecycleRuntimeKtx =
        "androidx.lifecycle:lifecycle-runtime-ktx:${Versions.lifecycleRuntimeKtx}"
    const val lifecycleViewModelKtx =
        "androidx.lifecycle:lifecycle-viewmodel-ktx:${Versions.lifecycleViewModelKtx}"
    const val lifecycleExtensions =
        "androidx.lifecycle:lifecycle-extensions:${Versions.lifecycleExtensions}"
}
