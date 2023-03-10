package ru.gortea.petter.auth.registration.registration_form.presentation.state

internal data class RegistrationFieldState(
    val text: String = "",
    val isValid: Boolean = true,
    val isVisible: Boolean = true
)

internal fun RegistrationFieldState.isNotBlank(): Boolean = text.isNotBlank()

internal fun RegistrationFieldState.hide(): RegistrationFieldState {
    return copy(isVisible = false)
}

internal fun RegistrationFieldState.valid(): RegistrationFieldState {
    return copy(isValid = true)
}

internal fun RegistrationFieldState.invalid(): RegistrationFieldState {
    return copy(isValid = false)
}
