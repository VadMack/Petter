object Libs {

    private object Versions {
        const val coroutines = "1.6.1"
        const val coroutinesRx = "1.6.4"
        const val serialization = "1.5.0-RC"
        const val coil = "2.2.2"
        const val compressor = "3.0.1"
        const val okHttp = "4.9.3"
        const val retrofit = "2.9.0"
        const val retrofitSerializationConverter = "0.8.0"
        const val leakCanary = "2.9.1"
        const val dagger2 = "2.17"
        const val appyx = "1.1.1"
        const val room = "2.5.0"
        const val stomp = "1.6.6"
        const val rxJava = "2.2.21"
        const val rxJavaAndroid = "2.1.1"
        const val koTest = "5.6.1"
        const val turbine = "0.12.3"
    }

    const val turbine = "app.cash.turbine:turbine:${Versions.turbine}"

    const val koTestJUnit = "io.kotest:kotest-runner-junit5:${Versions.koTest}"
    const val koTestAssertions = "io.kotest:kotest-assertions-core:${Versions.koTest}"
    const val koTestProperty = "io.kotest:kotest-property:${Versions.koTest}"

    const val stomp = "com.github.NaikSoftware:StompProtocolAndroid:${Versions.stomp}"
    const val rxJava = "io.reactivex.rxjava2:rxjava:${Versions.rxJava}"
    const val rxJavaAndroid = "io.reactivex.rxjava2:rxandroid:${Versions.rxJavaAndroid}"

    const val coroutines = "org.jetbrains.kotlinx:kotlinx-coroutines-android:${Versions.coroutines}"
    const val coroutinesRx = "org.jetbrains.kotlinx:kotlinx-coroutines-rx2:${Versions.coroutinesRx}"
    const val serialization =
        "org.jetbrains.kotlinx:kotlinx-serialization-json:${Versions.serialization}"

    const val dagger2 = "com.google.dagger:dagger:${Versions.dagger2}"
    const val dagger2Compiler = "com.google.dagger:dagger-compiler:${Versions.dagger2}"

    const val appyx = "com.bumble.appyx:core:${Versions.appyx}"

    const val room = "androidx.room:room-common:${Versions.room}"
    const val roomKtx = "androidx.room:room-ktx:${Versions.room}"
    const val roomCompiler = "androidx.room:room-compiler:${Versions.room}"

    const val coil = "io.coil-kt:coil-compose:${Versions.coil}"

    const val okHttp = "com.squareup.okhttp3:okhttp:${Versions.okHttp}"
    const val okHttpLoggingInterceptor =
        "com.squareup.okhttp3:logging-interceptor:${Versions.okHttp}"

    const val retrofit = "com.squareup.retrofit2:retrofit:${Versions.retrofit}"
    const val retrofitSerializationConverter =
        "com.jakewharton.retrofit:retrofit2-kotlinx-serialization-converter:${Versions.retrofitSerializationConverter}"

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

object AndroidX {

    private object Versions {
        const val coreKtx = "1.9.0"
        const val splashScreen = "1.0.0"
        const val activityKtx = "1.6.1"
        const val material = "1.5.0"
        const val lifecycleRuntimeKtx = "2.5.1"
        const val lifecycleViewModelKtx = "2.4.1"
        const val composeUi = "1.3.3"
        const val composeMaterial = "1.4.0"
        const val composeFoundation = "1.3.1"
        const val composeUiTooling = "1.3.2"
        const val composeActivity = "1.6.1"
        const val composeViewModel = "2.5.1"
    }

    const val composeUi = "androidx.compose.ui:ui:${Versions.composeUi}"
    const val composeMaterial = "androidx.compose.material:material:${Versions.composeMaterial}"
    const val composeFoundation =
        "androidx.compose.foundation:foundation:${Versions.composeFoundation}"
    const val composePreview = "androidx.compose.ui:ui-tooling-preview:${Versions.composeUiTooling}"
    const val composeUiTooling = "androidx.compose.ui:ui-tooling:${Versions.composeUiTooling}"
    const val composeActivity = "androidx.activity:activity-compose:${Versions.composeActivity}"
    const val composeViewModel =
        "androidx.lifecycle:lifecycle-viewmodel-compose:${Versions.composeViewModel}"

    const val coreKtx = "androidx.core:core-ktx:${Versions.coreKtx}"
    const val splashScreen = "androidx.core:core-splashscreen:${Versions.splashScreen}"
    const val activityKtx = "androidx.activity:activity-ktx:${Versions.activityKtx}"

    const val material = "com.google.android.material:material:${Versions.material}"
    const val lifecycleRuntimeKtx =
        "androidx.lifecycle:lifecycle-runtime-ktx:${Versions.lifecycleRuntimeKtx}"
    const val lifecycleViewModelKtx =
        "androidx.lifecycle:lifecycle-viewmodel-ktx:${Versions.lifecycleViewModelKtx}"
}
