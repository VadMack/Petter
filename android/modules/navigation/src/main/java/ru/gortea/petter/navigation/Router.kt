package ru.gortea.petter.navigation

interface Router<in T : NavTarget> : CommandController {
    fun updateRoot(target: T)
    fun navigateTo(target: T)
    fun restoreIfExists(target: T)
    fun pop()
}
