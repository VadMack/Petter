package ru.gortea.petter.auth.validation

internal interface Validator<T> {

    fun validate(value: T): Boolean
}
