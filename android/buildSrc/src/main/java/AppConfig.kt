import org.gradle.api.JavaVersion

object AppConfig {
    const val applicationId = "ru.gortea.petter"
    const val compileSdk = 33
    const val minSdk = 26
    const val targetSdk = 33
    const val versionCode = 1
    const val versionName = "1.0"

    object CompileOptions {
        val javaSourceCompatibility = JavaVersion.VERSION_1_8
        const val kotlinJvmTarget = "1.8"
    }
}
