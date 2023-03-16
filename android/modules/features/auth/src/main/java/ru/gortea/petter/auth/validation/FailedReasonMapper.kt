package ru.gortea.petter.auth.validation

internal fun interface FailedReasonMapper<T: FailedReason> {
    fun map(validator: Validator<*>): T
}
