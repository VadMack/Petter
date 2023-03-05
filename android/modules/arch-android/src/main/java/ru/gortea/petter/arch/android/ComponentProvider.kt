package ru.gortea.petter.arch.android

interface ComponentProvider {
    fun <T> getComponent(): T
}
