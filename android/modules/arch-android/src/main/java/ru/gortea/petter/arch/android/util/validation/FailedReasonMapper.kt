package ru.gortea.petter.arch.android.util.validation

fun interface FailedReasonMapper<T: FailedReason> {
    fun map(validator: Validator<*>): T
}
