pluginManagement.apply {
    repositories.apply {
        gradlePluginPortal()
        google()
        mavenCentral()
    }
}

rootProject.name = "Petter"
include(":app")

includeModulesInRoot("modules")

fun includeModulesInRoot(root: String) {
    val rootFile = file(root)
    rootFile.walkTopDown().maxDepth(3).forEach { file ->
        if (file.isModule()) {
            val modulePath = file.path.drop(rootFile.path.length)
            val module = ":$root${modulePath.replace("/", ":")}"
            include(module)
        }
    }
}

fun File.isModule(): Boolean {
    val isGroovyModule = File(this, "build.gradle").exists()
    val isKtsModule = File(this, "build.gradle.kts").exists()
    return isGroovyModule || isKtsModule
}
