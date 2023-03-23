package ru.gortea.petter.arch.android.util.validation

open class ValidatorComposite<T, R: FailedReason>(
    private val failedReasonMapper: FailedReasonMapper<R>,
    vararg validators: Validator<T>
) : Validator<T> {
    private val validatorsList = listOf(*validators)

    fun validateWithReasons(value: T): List<R> {
        val failedValidators = mutableListOf<Validator<T>>()

        validatorsList.forEach {
            if (!it.validate(value)) failedValidators.add(it)
        }

        return failedValidators.map(failedReasonMapper::map)
    }

    override fun validate(value: T): Boolean {
        return validatorsList.all { it.validate(value) }
    }
}
