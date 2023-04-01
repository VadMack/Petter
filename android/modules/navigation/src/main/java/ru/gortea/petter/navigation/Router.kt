package ru.gortea.petter.navigation

interface Router<in T : NavTarget> {
    fun updateRoot(target: T)
    fun navigateTo(target: T)
    fun restoreIfExists(target: T)
    fun pop(current: Boolean = false)
}
