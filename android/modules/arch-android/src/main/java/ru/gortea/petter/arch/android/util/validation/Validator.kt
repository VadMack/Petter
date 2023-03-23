package ru.gortea.petter.arch.android.util.validation

interface Validator<T> {

    fun validate(value: T): Boolean
}
